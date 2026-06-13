package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.ProductHistory;
import com.example.demo.model.PurchaseOrder;
import com.example.demo.repository.ProductHistoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductHistoryRepository historyRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    private static final DateTimeFormatter MONTH_YEAR_FORMAT = DateTimeFormatter.ofPattern("MMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @GetMapping
    public ResponseEntity<InventoryResponseDto> getInventory(
            @RequestParam(value = "category", defaultValue = "All Products") String category,
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "expiryFilter", defaultValue = "all") String expiryFilter,
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<Product> allProducts = productRepository.findAll();

        // 1. Calculate Metrics (from all products)
        long totalCount = allProducts.size();
        long goodStockCount = allProducts.stream()
                .filter(p -> p.getUnits() > 400 && p.getStockPercent() > 40)
                .count();
        long lowStockCount = allProducts.stream()
                .filter(p -> p.getStockPercent() > 20 && p.getStockPercent() <= 40)
                .count();
        long criticalStockCount = allProducts.stream()
                .filter(p -> p.getStockPercent() <= 20)
                .count();
        long expiringSoonCount = allProducts.stream()
                .filter(p -> {
                    long days = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), p.getExpiryDate());
                    return days >= 0 && days <= 60;
                })
                .count();

        double goodPercent = totalCount > 0 ? (goodStockCount * 100.0 / totalCount) : 0.0;

        List<MetricDto> metrics = new ArrayList<>();
        metrics.add(new MetricDto("Total Products", String.valueOf(totalCount), "↑ 124 added this month", "up"));
        metrics.add(new MetricDto("In Stock", String.valueOf(goodStockCount), String.format(Locale.ENGLISH, "%.1f%% of total", goodPercent), "up"));
        metrics.add(new MetricDto("Low Stock", String.valueOf(lowStockCount), "Reorder needed", "neutral"));
        metrics.add(new MetricDto("Critical / OOS", String.valueOf(criticalStockCount), "↑ Requires attention", "down"));
        metrics.add(new MetricDto("Expiring Soon", String.valueOf(expiringSoonCount), "Within 60 days", "neutral"));

        // 2. Filter Products
        List<Product> filtered = allProducts.stream()
                .filter(p -> {
                    // Category Filter
                    if ("Critical".equalsIgnoreCase(category)) {
                        return "Critical".equalsIgnoreCase(computeStatus(p.getUnits(), p.getStockPercent()));
                    } else if (!"All Products".equalsIgnoreCase(category)) {
                        return p.getCategory().equalsIgnoreCase(category);
                    }
                    return true;
                })
                .filter(p -> {
                    // Search Query Filter
                    if (search == null || search.trim().isEmpty()) {
                        return true;
                    }
                    String term = search.toLowerCase();
                    return p.getName().toLowerCase().contains(term)
                            || p.getBatch().toLowerCase().contains(term)
                            || p.getSupplierName().toLowerCase().contains(term);
                })
                .filter(p -> {
                    // Expiry Date Filter
                    if ("all".equalsIgnoreCase(expiryFilter)) {
                        return true;
                    }
                    try {
                        int daysLimit = Integer.parseInt(expiryFilter);
                        long days = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), p.getExpiryDate());
                        return days >= 0 && days <= daysLimit;
                    } catch (NumberFormatException e) {
                        return true;
                    }
                })
                .collect(Collectors.toList());

        // 3. Sort Products
        if (sortColumn != null && !sortColumn.trim().isEmpty()) {
            final String col = sortColumn.trim();
            final boolean desc = "desc".equalsIgnoreCase(sortDirection);
            filtered.sort((a, b) -> {
                int cmp = 0;
                switch (col) {
                    case "name":
                        cmp = a.getName().compareToIgnoreCase(b.getName());
                        break;
                    case "stock":
                        cmp = Integer.compare(a.getStockPercent(), b.getStockPercent());
                        break;
                    case "units":
                        cmp = Integer.compare(a.getUnits(), b.getUnits());
                        break;
                    case "unitPrice":
                    case "unitPriceNum":
                        cmp = a.getUnitPrice().compareTo(b.getUnitPrice());
                        break;
                    case "expiry":
                        cmp = a.getExpiryDate().compareTo(b.getExpiryDate());
                        break;
                    case "status":
                        List<String> order = Arrays.asList("Critical", "OOS", "Low", "Good");
                        int oA = order.indexOf(computeStatus(a.getUnits(), a.getStockPercent()));
                        int oB = order.indexOf(computeStatus(b.getUnits(), b.getStockPercent()));
                        cmp = Integer.compare(oA, oB);
                        break;
                }
                return desc ? -cmp : cmp;
            });
        }

        // 4. Paginate Results
        int totalItems = filtered.size();
        int totalPages = (int) Math.ceil((double) totalItems / limit);
        int start = (page - 1) * limit;
        int end = Math.min(start + limit, totalItems);

        List<Product> paginated = (start < totalItems && start >= 0)
                ? filtered.subList(start, end)
                : new ArrayList<>();

        // 5. Map to DTOs
        List<InventoryProductDto> dataList = paginated.stream()
                .map(p -> {
                    InventoryProductDto dto = new InventoryProductDto();
                    dto.setId(p.getId());
                    dto.setName(p.getName());
                    dto.setCategory(p.getCategory());
                    dto.setBatch(p.getBatch());
                    dto.setSku(p.getSku());
                    dto.setStock(p.getStockPercent());
                    dto.setUnits(p.getUnits());
                    dto.setUnitPriceNum(p.getUnitPrice().doubleValue());
                    dto.setUnitPrice(formatIndianRupees(p.getUnitPrice()));
                    dto.setExpiry(p.getExpiryDate().format(MONTH_YEAR_FORMAT));
                    dto.setExpiryDate(p.getExpiryDate().atStartOfDay(ZoneOffset.UTC).format(ISO_FORMAT));

                    long days = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), p.getExpiryDate());
                    if (days <= 30) {
                        dto.setExpStatus("low");
                    } else if (days <= 90) {
                        dto.setExpStatus("warn");
                    } else {
                        dto.setExpStatus("ok");
                    }

                    dto.setSupplier(p.getSupplierName());
                    dto.setStatus(computeStatus(p.getUnits(), p.getStockPercent()));

                    // Fetch history logs
                    List<ProductHistory> historyLogs = historyRepository.findByProductIdOrderByTransactionDateDesc(p.getId());
                    List<HistoryDto> historyDtos = historyLogs.stream()
                            .map(h -> new HistoryDto(
                                    h.getTransactionDate().format(MONTH_YEAR_FORMAT),
                                    h.getQuantity(),
                                    formatIndianRupees(h.getCost())
                            ))
                            .collect(Collectors.toList());
                    dto.setHistory(historyDtos);

                    return dto;
                })
                .collect(Collectors.toList());

        PaginationDto pagination = new PaginationDto(page, totalPages, totalItems, limit);
        return ResponseEntity.ok(new InventoryResponseDto(metrics, dataList, pagination));
    }

    @PostMapping
    public ResponseEntity<Object> addProduct(@RequestBody Map<String, Object> body) {
        try {
            String name = (String) body.get("name");
            String category = (String) body.get("category");
            String batch = (String) body.get("batch");
            String sku = (String) body.get("sku");
            int stock = ((Number) body.getOrDefault("stock", 100)).intValue();
            int units = ((Number) body.getOrDefault("units", 0)).intValue();
            double unitPriceNum = ((Number) body.get("unitPriceNum")).doubleValue();
            String expiryDateStr = (String) body.get("expiryDate");
            String supplier = (String) body.getOrDefault("supplier", "Sun Pharma");

            // Generate next ID PRD-XXX
            List<Product> all = productRepository.findAll();
            int maxId = 6;
            for (Product p : all) {
                if (p.getId().startsWith("PRD-")) {
                    try {
                        int num = Integer.parseInt(p.getId().substring(4));
                        if (num > maxId) maxId = num;
                    } catch (Exception e) {}
                }
            }
            String newId = String.format("PRD-%03d", maxId + 1);

            Product product = new Product();
            product.setId(newId);
            product.setName(name);
            product.setCategory(category);
            product.setBatch(batch);
            product.setSku(sku);
            product.setStockPercent(stock);
            product.setUnits(units);
            product.setUnitPrice(BigDecimal.valueOf(unitPriceNum));
            product.setExpiryDate(LocalDate.parse(expiryDateStr));
            product.setSupplierName(supplier);

            Product saved = productRepository.save(product);

            // Log initial history transaction
            ProductHistory history = new ProductHistory();
            history.setProductId(newId);
            history.setTransactionDate(LocalDate.now());
            history.setQuantity(units + " units");
            history.setCost(BigDecimal.valueOf(units * unitPriceNum));
            historyRepository.save(history);

            // Return response object
            InventoryProductDto dto = new InventoryProductDto();
            dto.setId(saved.getId());
            dto.setName(saved.getName());
            dto.setCategory(saved.getCategory());
            dto.setBatch(saved.getBatch());
            dto.setSku(saved.getSku());
            dto.setStock(saved.getStockPercent());
            dto.setUnits(saved.getUnits());
            dto.setUnitPriceNum(saved.getUnitPrice().doubleValue());
            dto.setUnitPrice(formatIndianRupees(saved.getUnitPrice()));
            dto.setExpiry(saved.getExpiryDate().format(MONTH_YEAR_FORMAT));
            dto.setExpiryDate(saved.getExpiryDate().atStartOfDay(ZoneOffset.UTC).format(ISO_FORMAT));
            dto.setExpStatus("ok");
            dto.setSupplier(saved.getSupplierName());
            dto.setStatus(computeStatus(saved.getUnits(), saved.getStockPercent()));
            dto.setHistory(Collections.singletonList(new HistoryDto(
                    LocalDate.now().format(MONTH_YEAR_FORMAT),
                    units + " units",
                    formatIndianRupees(history.getCost())
            )));

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(err);
        }
    }

    @PostMapping("/reorder")
    public ResponseEntity<Object> reorderProduct(@RequestBody Map<String, Object> body) {
        try {
            String productId = (String) body.get("productId");
            int quantity = ((Number) body.get("quantity")).intValue();
            String supplier = (String) body.get("supplier");
            String deliveryDateStr = (String) body.get("deliveryDate");

            Optional<Product> prodOpt = productRepository.findById(productId);
            if (prodOpt.isEmpty()) {
                Map<String, String> err = new HashMap<>();
                err.put("error", "Product not found: " + productId);
                return ResponseEntity.status(404).body(err);
            }

            Product product = prodOpt.get();

            // 1. Log Purchase Order
            String poId = "PO-" + System.currentTimeMillis();
            PurchaseOrder order = new PurchaseOrder();
            order.setId(poId);
            order.setProductId(productId);
            order.setQuantity(quantity);
            order.setSupplierName(supplier);
            order.setDeliveryDate(LocalDate.parse(deliveryDateStr));
            purchaseOrderRepository.save(order);

            // 2. Update Product stock & units
            int currentUnits = product.getUnits();
            int newUnits = currentUnits + quantity;
            int currentStock = product.getStockPercent();
            int newStock = Math.min(100, currentStock + (int) Math.floor(((double) quantity / Math.max(currentUnits, 1)) * 100));

            product.setUnits(newUnits);
            product.setStockPercent(newStock);
            productRepository.save(product);

            // 3. Log transaction history
            ProductHistory history = new ProductHistory();
            history.setProductId(productId);
            history.setTransactionDate(LocalDate.now());
            history.setQuantity(quantity + " units");
            history.setCost(product.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
            historyRepository.save(history);

            // 4. Return response
            Map<String, Object> res = new HashMap<>();
            res.put("orderId", poId);
            res.put("productId", productId);
            res.put("newUnits", newUnits);
            res.put("status", "ordered");
            res.put("createdAt", LocalDateTime.now().atZone(ZoneOffset.UTC).format(ISO_FORMAT));

            return ResponseEntity.ok(res);

        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(err);
        }
    }

    // ──────────────────────────────────────────────────────────
    // POST /api/inventory/bulk-add
    // Accepts a list of CSV-parsed products and processes each
    // one: inserts into the products table + logs product_history.
    // ──────────────────────────────────────────────────────────
    @PostMapping("/bulk-add")
    @Transactional
    public ResponseEntity<Object> bulkAdd(@RequestBody Map<String, Object> body) {
        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> products = (List<Map<String, Object>>) body.get("products");

            if (products == null || products.isEmpty()) {
                Map<String, String> err = new HashMap<>();
                err.put("error", "No products provided in request body");
                return ResponseEntity.badRequest().body(err);
            }

            int totalRows   = products.size();
            int successRows = 0;
            int failedRows  = 0;

            List<Map<String, Object>> addedProducts = new ArrayList<>();

            // Find max existing ID to auto-increment
            List<Product> all = productRepository.findAll();
            int maxId = 0;
            for (Product p : all) {
                if (p.getId().startsWith("PRD-")) {
                    try {
                        int num = Integer.parseInt(p.getId().substring(4));
                        if (num > maxId) maxId = num;
                    } catch (Exception e) {}
                }
            }

            for (Map<String, Object> row : products) {
                try {
                    String name = (String) row.get("name");
                    String category = (String) row.get("category");
                    String batch = (String) row.get("batch");
                    String sku = (String) row.get("sku");
                    int stock = row.get("stock") != null && !row.get("stock").toString().isBlank() ? Integer.parseInt(row.get("stock").toString()) : 50;
                    int units = row.get("units") != null && !row.get("units").toString().isBlank() ? Integer.parseInt(row.get("units").toString()) : 0;
                    double unitPriceNum = row.get("unitPrice") != null && !row.get("unitPrice").toString().isBlank() ? Double.parseDouble(row.get("unitPrice").toString()) : 0.0;
                    String expiryDateStr = (String) row.get("expiryDate");
                    String supplier = (String) row.getOrDefault("supplier", "Sun Pharma");

                    maxId++;
                    String newId = String.format("PRD-%03d", maxId);

                    Product product = new Product();
                    product.setId(newId);
                    product.setName(name);
                    product.setCategory(category);
                    product.setBatch(batch);
                    product.setSku(sku);
                    product.setStockPercent(stock);
                    product.setUnits(units);
                    product.setUnitPrice(BigDecimal.valueOf(unitPriceNum));
                    product.setExpiryDate(LocalDate.parse(expiryDateStr));
                    product.setSupplierName(supplier);

                    productRepository.save(product);

                    // Log initial history transaction
                    ProductHistory history = new ProductHistory();
                    history.setProductId(newId);
                    history.setTransactionDate(LocalDate.now());
                    history.setQuantity(units + " units");
                    history.setCost(BigDecimal.valueOf(units * unitPriceNum));
                    historyRepository.save(history);

                    // Build per-product result
                    Map<String, Object> productResult = new HashMap<>();
                    productResult.put("name", name);
                    productResult.put("category", category);
                    productResult.put("units", units);
                    productResult.put("supplier", supplier);
                    addedProducts.add(productResult);

                    successRows++;

                } catch (Exception rowEx) {
                    System.err.println("[BulkAdd] ERROR processing row: "
                            + rowEx.getClass().getSimpleName() + " — " + rowEx.getMessage());
                    rowEx.printStackTrace(System.err);
                    failedRows++;
                }
            }

            // Build and return the BulkUploadResult response
            Map<String, Object> result = new HashMap<>();
            result.put("totalRows",       totalRows);
            result.put("successRows",     successRows);
            result.put("failedRows",      failedRows);
            result.put("addedProducts",   addedProducts);

            System.out.println("[BulkAdd] Complete — success: " + successRows + ", failed: " + failedRows);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            System.err.println("[BulkAdd] FATAL ERROR: " + e.getMessage());
            e.printStackTrace(System.err);
            Map<String, String> err = new HashMap<>();
            err.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(err);
        }
    }


    private String computeStatus(int units, int stockPercent) {
        if (units <= 0) return "OOS";
        if (stockPercent <= 20) return "Critical";
        if (stockPercent <= 40) return "Low";
        return "Good";
    }

    private String formatIndianRupees(BigDecimal amount) {
        if (amount == null) return "₹0";
        long val = amount.longValue();
        String str = String.valueOf(val);
        if (str.length() <= 3) {
            return "₹" + str;
        }
        String lastThree = str.substring(str.length() - 3);
        String remaining = str.substring(0, str.length() - 3);
        StringBuilder sb = new StringBuilder();
        int len = remaining.length();
        for (int i = 0; i < len; i++) {
            if (i > 0 && (len - i) % 2 == 0) {
                sb.append(",");
            }
            sb.append(remaining.charAt(i));
        }
        return "₹" + sb.toString() + "," + lastThree;
    }
}
