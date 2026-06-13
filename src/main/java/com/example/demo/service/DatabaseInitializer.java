package com.example.demo.service;

import com.example.demo.model.PharmaOrder;
import com.example.demo.repository.PharmaOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private PharmaOrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        if (orderRepository.count() == 0) {
            List<PharmaOrder> orders = new ArrayList<>();

            // 1
            orders.add(createOrder("ORD-2841", "City Hospital", "Chennai", "Antibiotics, Vitamins", "₹1,24,000", "delivered", 24, "10 May 2026",
                    "14, Anna Salai, Chennai – 600002", "+91 44 2345 6789", "33AABCC1234D1Z5",
                    List.of(
                            createProduct("Amoxicillin 500mg", "200 strips", "₹42,000"),
                            createProduct("Vitamin C 1000mg", "150 boxes", "₹36,000"),
                            createProduct("Azithromycin 250mg", "100 strips", "₹46,000")
                    ),
                    List.of(
                            createTimeline("10 May, 09:14 AM", "Order Placed", "By Dr. Ramesh Kumar", true),
                            createTimeline("10 May, 11:30 AM", "Order Confirmed", "Verified by warehouse", true),
                            createTimeline("10 May, 03:00 PM", "Dispatched", "Via BlueDart · TRK8423842", true),
                            createTimeline("11 May, 10:20 AM", "Delivered", "Received by Dr. Ramesh", true)
                    )
            ));

            // 2
            orders.add(createOrder("ORD-2840", "Apollo Pharmacy", "Bengaluru", "Cardiology", "₹89,500", "processing", 16, "10 May 2026",
                    "27, MG Road, Bengaluru – 560001", "+91 80 4567 8901", "29AABCA5678E2Z3",
                    List.of(
                            createProduct("Atorvastatin 40mg", "300 strips", "₹54,000"),
                            createProduct("Metoprolol 50mg", "200 strips", "₹35,500")
                    ),
                    List.of(
                            createTimeline("10 May, 08:30 AM", "Order Placed", "Online portal", true),
                            createTimeline("10 May, 10:00 AM", "Payment Confirmed", "NEFT ₹89,500", true),
                            createTimeline("10 May, 02:00 PM", "Processing", "Picking & packing", true),
                            createTimeline("Pending", "Dispatch", "Estimated 11 May", false)
                    )
            ));

            // 3
            orders.add(createOrder("ORD-2839", "MedPlus", "Hyderabad", "Diabetes, Supplements", "₹2,18,750", "shipped", 38, "09 May 2026",
                    "88, Banjara Hills, Hyderabad – 500034", "+91 40 6789 0123", "36AABCM3456F3Z1",
                    List.of(
                            createProduct("Metformin 1000mg", "500 strips", "₹95,000"),
                            createProduct("Glipizide 5mg", "400 strips", "₹68,000"),
                            createProduct("Vitamin D3 1000IU", "300 boxes", "₹55,750")
                    ),
                    List.of(
                            createTimeline("09 May, 07:00 AM", "Order Placed", "Bulk order", true),
                            createTimeline("09 May, 09:00 AM", "Confirmed & Invoiced", "Invoice #INV-3341", true),
                            createTimeline("09 May, 04:00 PM", "Dispatched", "DHL · DHLIND8823", true),
                            createTimeline("Pending", "Delivery", "Expected 12 May", false)
                    )
            ));

            // 4
            orders.add(createOrder("ORD-2838", "Fortis Pharmacy", "Mumbai", "Antibiotics", "₹45,200", "delivered", 12, "09 May 2026",
                    "Fortis Hospital, Mulund, Mumbai – 400080", "+91 22 2345 0987", "27AABCF7890G4Z2",
                    List.of(
                            createProduct("Cephalexin 500mg", "120 strips", "₹28,800"),
                            createProduct("Doxycycline 100mg", "80 strips", "₹16,400")
                    ),
                    List.of(
                            createTimeline("09 May, 06:30 AM", "Order Placed", null, true),
                            createTimeline("09 May, 08:00 AM", "Confirmed", null, true),
                            createTimeline("09 May, 01:00 PM", "Dispatched", "DTDC · DTDC7712", true),
                            createTimeline("10 May, 09:00 AM", "Delivered", "Received by pharmacy", true)
                    )
            ));

            // 5
            orders.add(createOrder("ORD-2837", "LifeCare Stores", "Pune", "Vitamins, Analgesics", "₹67,300", "cancelled", 9, "08 May 2026",
                    "FC Road, Shivajinagar, Pune – 411005", "+91 20 3456 7890", "27AABCL2345H5Z8",
                    List.of(
                            createProduct("Paracetamol 650mg", "200 strips", "₹24,000"),
                            createProduct("Vitamin B12", "150 boxes", "₹43,300")
                    ),
                    List.of(
                            createTimeline("08 May, 10:00 AM", "Order Placed", null, true),
                            createTimeline("08 May, 11:30 AM", "Confirmed", null, true),
                            createTimeline("08 May, 03:00 PM", "Cancelled by customer", "Reason: Duplicate order", true)
                    )
            ));

            // 6
            orders.add(createOrder("ORD-2836", "Medanta", "New Delhi", "Cardiology, Diabetes", "₹3,42,000", "delivered", 52, "08 May 2026",
                    "Sector 38, Gurgaon, Haryana – 122001", "+91 124 4141414", "06AABCM1234I6Z4",
                    List.of(
                            createProduct("Amlodipine 10mg", "600 strips", "₹1,20,000"),
                            createProduct("Metformin 500mg", "800 strips", "₹1,12,000"),
                            createProduct("Rosuvastatin 20mg", "400 strips", "₹1,10,000")
                    ),
                    List.of(
                            createTimeline("08 May, 06:00 AM", "Bulk Order Placed", null, true),
                            createTimeline("08 May, 08:30 AM", "Verified & Approved", null, true),
                            createTimeline("08 May, 02:00 PM", "Dispatched", "Own logistics", true),
                            createTimeline("09 May, 11:00 AM", "Delivered", null, true)
                    )
            ));

            // 7
            orders.add(createOrder("ORD-2835", "Max Healthcare", "Noida", "Antibiotics", "₹98,400", "delivered", 20, "07 May 2026",
                    "Max Super Speciality, Sector 19, Noida – 201301", "+91 120 4567890", "09AABCM9012J7Z3",
                    List.of(
                            createProduct("Meropenem 1g", "50 vials", "₹60,000"),
                            createProduct("Vancomycin 500mg", "80 vials", "₹38,400")
                    ),
                    List.of(
                            createTimeline("07 May, 09:00 AM", "Order Placed", null, true),
                            createTimeline("07 May, 10:30 AM", "Confirmed", null, true),
                            createTimeline("07 May, 04:30 PM", "Dispatched", null, true),
                            createTimeline("08 May, 10:00 AM", "Delivered", null, true)
                    )
            ));

            // 8
            orders.add(createOrder("ORD-2834", "Cloudnine Hospital", "Bengaluru", "Supplements, Vitamins", "₹56,700", "delivered", 30, "07 May 2026",
                    "Cloudnine Hospital, Jayanagar, Bengaluru – 560041", "+91 80 6789 1234", "29AABCC6789K8Z7",
                    List.of(
                            createProduct("Folic Acid 5mg", "300 strips", "₹18,000"),
                            createProduct("Iron + Folic", "250 strips", "₹22,500"),
                            createProduct("Calcium + Vit D3", "200 boxes", "₹16,200")
                    ),
                    List.of(
                            createTimeline("07 May, 07:30 AM", "Order Placed", null, true),
                            createTimeline("07 May, 09:00 AM", "Confirmed", null, true),
                            createTimeline("07 May, 02:00 PM", "Dispatched", null, true),
                            createTimeline("08 May, 09:30 AM", "Delivered", null, true)
                    )
            ));

            // 9
            orders.add(createOrder("ORD-2833", "Narayana Health", "Kolkata", "Cardiology", "₹1,34,500", "delivered", 18, "06 May 2026",
                    "Narayana Multispeciality, Howrah, Kolkata – 711102", "+91 33 7122 7122", "19AABCN4567L9Z2",
                    List.of(
                            createProduct("Digoxin 0.25mg", "400 strips", "₹64,000"),
                            createProduct("Furosemide 40mg", "300 strips", "₹45,000"),
                            createProduct("Spironolactone 50mg", "200 strips", "₹25,500")
                    ),
                    List.of(
                            createTimeline("06 May, 08:00 AM", "Order Placed", null, true),
                            createTimeline("06 May, 10:00 AM", "Confirmed", null, true),
                            createTimeline("06 May, 03:00 PM", "Dispatched", null, true),
                            createTimeline("07 May, 02:00 PM", "Delivered", null, true)
                    )
            ));

            // 10
            orders.add(createOrder("ORD-2832", "Ruby Hall Clinic", "Pune", "Analgesics", "₹34,800", "processing", 14, "06 May 2026",
                    "40, Sassoon Road, Pune – 411001", "+91 20 6645 6645", "27AABCR3456M0Z6",
                    List.of(
                            createProduct("Tramadol 50mg", "200 strips", "₹18,000"),
                            createProduct("Diclofenac 75mg", "180 strips", "₹16,800")
                    ),
                    List.of(
                            createTimeline("06 May, 11:00 AM", "Order Placed", null, true),
                            createTimeline("06 May, 12:30 PM", "Payment Confirmed", null, true),
                            createTimeline("06 May, 04:00 PM", "Processing", "Warehouse picking", true),
                            createTimeline("Pending", "Dispatch", "Expected 07 May", false)
                    )
            ));

            // 11
            orders.add(createOrder("ORD-2831", "KIMS Hospital", "Hyderabad", "Antibiotics, Cardiology", "₹2,76,000", "delivered", 44, "05 May 2026",
                    "Minister Road, Secunderabad – 500003", "+91 40 4488 5000", "36AABCK5678N1Z9",
                    List.of(
                            createProduct("Piperacillin+Tazo", "100 vials", "₹1,20,000"),
                            createProduct("Clopidogrel 75mg", "500 strips", "₹87,500"),
                            createProduct("Aspirin 75mg", "600 strips", "₹68,500")
                    ),
                    List.of(
                            createTimeline("05 May, 07:00 AM", "Order Placed", null, true),
                            createTimeline("05 May, 09:00 AM", "Confirmed", null, true),
                            createTimeline("05 May, 01:00 PM", "Dispatched", null, true),
                            createTimeline("06 May, 10:00 AM", "Delivered", null, true)
                    )
            ));

            // 12
            orders.add(createOrder("ORD-2830", "SRL Diagnostics", "Mumbai", "Supplements", "₹22,400", "pending", 8, "05 May 2026",
                    "SRL House, Goregaon, Mumbai – 400062", "+91 22 6752 6752", "27AABCS7890O2Z5",
                    List.of(
                            createProduct("Zinc 50mg", "100 strips", "₹12,000"),
                            createProduct("Omega 3 1000mg", "80 boxes", "₹10,400")
                    ),
                    List.of(
                            createTimeline("05 May, 02:00 PM", "Order Placed", null, true),
                            createTimeline("Pending", "Payment Confirmation", "Awaiting NEFT", false)
                    )
            ));

            // 13
            orders.add(createOrder("ORD-2829", "Dr. Agarwal Eye Hospital", "New Delhi", "Supplements", "₹45,600", "delivered", 16, "04 May 2026",
                    "Dr. Agarwal Eye Care, Rajendra Place, New Delhi – 110008", "+91 11 4567 0000", "07AABCA8901P3Z1",
                    List.of(
                            createProduct("Lutein + Zeaxanthin", "200 boxes", "₹28,000"),
                            createProduct("Vitamin A 5000IU", "150 boxes", "₹17,600")
                    ),
                    List.of(
                            createTimeline("04 May, 10:00 AM", "Order Placed", null, true),
                            createTimeline("04 May, 11:30 AM", "Confirmed", null, true),
                            createTimeline("04 May, 03:00 PM", "Dispatched", null, true),
                            createTimeline("05 May, 10:00 AM", "Delivered", null, true)
                    )
            ));

            // 14
            orders.add(createOrder("ORD-2828", "Apollo Clinic", "Pune", "Cardiology, Diabetes", "₹1,78,900", "shipped", 32, "04 May 2026",
                    "Apollo Clinic, Bund Garden, Pune – 411001", "+91 20 6600 5000", "27AABCA2345Q7Z4",
                    List.of(
                            createProduct("Atenolol 50mg", "400 strips", "₹52,000"),
                            createProduct("Lisinopril 10mg", "300 strips", "₹66,000"),
                            createProduct("Glibenclamide 5mg", "250 strips", "₹60,900")
                    ),
                    List.of(
                            createTimeline("04 May, 08:00 AM", "Order Placed", null, true),
                            createTimeline("04 May, 09:30 AM", "Confirmed", null, true),
                            createTimeline("04 May, 02:00 PM", "Dispatched", "Fedex · FDX45823", true),
                            createTimeline("In Transit", "Delivery", "Expected 06 May", false)
                    )
            ));

            // 15
            orders.add(createOrder("ORD-2827", "Breach Candy Hospital", "Mumbai", "Antibiotics", "₹87,500", "delivered", 22, "03 May 2026",
                    "60-A, Bhulabhai Desai Road, Mumbai – 400026", "+91 22 2368 3888", "27AABCB6789R5Z8",
                    List.of(
                            createProduct("Levofloxacin 500mg", "300 strips", "₹45,000"),
                            createProduct("Cefixime 200mg", "250 strips", "₹42,500")
                    ),
                    List.of(
                            createTimeline("03 May, 09:00 AM", "Order Placed", null, true),
                            createTimeline("03 May, 10:30 AM", "Confirmed", null, true),
                            createTimeline("03 May, 03:30 PM", "Dispatched", null, true),
                            createTimeline("04 May, 11:00 AM", "Delivered", null, true)
                    )
            ));

            // 16
            orders.add(createOrder("ORD-2826", "Manipal Hospital", "Bengaluru", "Analgesics, Supplements", "₹63,200", "cancelled", 24, "03 May 2026",
                    "Manipal Hospital, CV Raman Nagar, Bengaluru – 560093", "+91 80 4044 0000", "29AABCM0123S6Z2",
                    List.of(
                            createProduct("Ibuprofen 400mg", "400 strips", "₹32,000"),
                            createProduct("Acetaminophen 500mg", "200 strips", "₹16,000"),
                            createProduct("Magnesium", "150 boxes", "₹15,200")
                    ),
                    List.of(
                            createTimeline("03 May, 11:00 AM", "Order Placed", null, true),
                            createTimeline("03 May, 01:00 PM", "Confirmed", null, true),
                            createTimeline("03 May, 04:00 PM", "Cancelled by customer", "Reason: Price negotiation", true)
                    )
            ));

            // 17
            orders.add(createOrder("ORD-2825", "St. Johns Medical", "Bengaluru", "Cardiology", "₹92,400", "delivered", 20, "02 May 2026",
                    "St. Johns Medical College Hospital, Bengaluru – 560034", "+91 80 4343 3333", "29AABCS9012T1Z7",
                    List.of(
                            createProduct("Ramipril 5mg", "300 strips", "₹54,000"),
                            createProduct("Nitroglycerin 0.6mg", "150 strips", "₹38,400")
                    ),
                    List.of(
                            createTimeline("02 May, 08:30 AM", "Order Placed", null, true),
                            createTimeline("02 May, 10:00 AM", "Confirmed", null, true),
                            createTimeline("02 May, 02:00 PM", "Dispatched", null, true),
                            createTimeline("03 May, 09:30 AM", "Delivered", null, true)
                    )
            ));

            // 18
            orders.add(createOrder("ORD-2824", "Vimta Labs", "Hyderabad", "Antibiotics", "₹1,12,600", "delivered", 28, "02 May 2026",
                    "Vimta Labs, Madhapur, Hyderabad – 500081", "+91 40 2358 6789", "36AABCV2345U8Z4",
                    List.of(
                            createProduct("Ciprofloxacin 500mg", "400 strips", "₹56,000"),
                            createProduct("Gentamicin 80mg", "200 vials", "₹56,600")
                    ),
                    List.of(
                            createTimeline("02 May, 07:00 AM", "Order Placed", null, true),
                            createTimeline("02 May, 08:30 AM", "Confirmed", null, true),
                            createTimeline("02 May, 01:00 PM", "Dispatched", null, true),
                            createTimeline("03 May, 10:00 AM", "Delivered", null, true)
                    )
            ));

            // 19
            orders.add(createOrder("ORD-2823", "Fortis Escorts Hospital", "New Delhi", "Cardiology, Antibiotics", "₹2,34,500", "processing", 36, "01 May 2026",
                    "Fortis Escorts Hospital, Okhla Road, New Delhi – 110025", "+91 11 4200 4200", "07AABCF3456V9Z6",
                    List.of(
                            createProduct("Verapamil 120mg", "300 strips", "₹1,05,000"),
                            createProduct("Imipenem + Cilastatin", "80 vials", "₹96,000"),
                            createProduct("Doxycycline 100mg", "300 strips", "₹33,500")
                    ),
                    List.of(
                            createTimeline("01 May, 06:00 AM", "Order Placed", null, true),
                            createTimeline("01 May, 08:00 AM", "Verified & Approved", null, true),
                            createTimeline("01 May, 02:00 PM", "Processing", "Warehouse picking in progress", true),
                            createTimeline("Pending", "Dispatch", "Expected 02 May", false)
                    )
            ));

            // 20
            orders.add(createOrder("ORD-2822", "Lilavati Hospital", "Mumbai", "Vitamins", "₹78,400", "delivered", 32, "01 May 2026",
                    "15, New Marine Lines, Mumbai – 400020", "+91 22 6767 7777", "27AABCL4567W2Z9",
                    List.of(
                            createProduct("Vitamin C 500mg", "500 boxes", "₹45,000"),
                            createProduct("Vitamin E 400IU", "300 boxes", "₹33,400")
                    ),
                    List.of(
                            createTimeline("01 May, 10:00 AM", "Order Placed", null, true),
                            createTimeline("01 May, 11:30 AM", "Confirmed", null, true),
                            createTimeline("01 May, 03:00 PM", "Dispatched", null, true),
                            createTimeline("02 May, 10:00 AM", "Delivered", null, true)
                    )
            ));

            // 21
            orders.add(createOrder("ORD-2821", "Reliance Health", "Jaipur", "Diabetes", "₹1,45,800", "delivered", 40, "30 Apr 2026",
                    "Reliance Hospital, Jaipur – 302001", "+91 141 5166 5166", "08AABCR5678X3Z5",
                    List.of(
                            createProduct("Insulin Glargine", "30 pens", "₹75,000"),
                            createProduct("Pioglitazone 15mg", "400 strips", "₹42,800"),
                            createProduct("Sitagliptin 50mg", "300 strips", "₹28,000")
                    ),
                    List.of(
                            createTimeline("30 Apr, 08:00 AM", "Order Placed", null, true),
                            createTimeline("30 Apr, 09:30 AM", "Confirmed", null, true),
                            createTimeline("30 Apr, 01:00 PM", "Dispatched", null, true),
                            createTimeline("01 May, 04:00 PM", "Delivered", null, true)
                    )
            ));

            orderRepository.saveAll(orders);
            System.out.println(">>> Database initialized with " + orders.size() + " pharma orders.");
        }
    }

    private PharmaOrder createOrder(String orderNum, String customer, String city, String category, String amount, String status,
                                    int items, String date, String address, String phone, String gstin,
                                    List<Map<String, Object>> products, List<Map<String, Object>> timeline) {
        PharmaOrder o = new PharmaOrder();
        o.setOrderNumber(orderNum);
        o.setCustomer(customer);
        o.setCity(city);
        o.setCategory(category);
        o.setAmount(amount);
        o.setStatus(status);
        o.setItems(items);
        o.setDate(date);
        o.setAddress(address);
        o.setPhone(phone);
        o.setGstin(gstin);
        o.setProducts(products);
        o.setTimeline(timeline);
        return o;
    }

    private Map<String, Object> createProduct(String name, String qty, String amt) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("qty", qty);
        map.put("amt", amt);
        return map;
    }

    private Map<String, Object> createTimeline(String time, String event, String sub, boolean done) {
        Map<String, Object> map = new HashMap<>();
        map.put("time", time);
        map.put("event", event);
        if (sub != null) {
            map.put("sub", sub);
        }
        map.put("done", done);
        return map;
    }
}
