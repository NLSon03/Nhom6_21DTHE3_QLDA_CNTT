package com.qlda.nhom6.controller;

import com.qlda.nhom6.config.Config;
import com.qlda.nhom6.model.CartItem;
import com.qlda.nhom6.model.Order;
import com.qlda.nhom6.service.CartService;
import com.qlda.nhom6.service.OrderService;
import com.qlda.nhom6.service.VNPayService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private VNPayService vnpayService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @GetMapping
    public  String ShowallNSX(@NotNull Model model){
        model.addAttribute("orders",orderService.getAllOrder());

        return"order/list";

    }

    @GetMapping("/checkout")
    public String checkout() {
        return "/cart/checkout";
    }
    @PostMapping("/submit")
    public ModelAndView submitOrder(String customerName, String shippingAddress, String phoneNumber, String email, String notes, String paymentMethod) throws UnsupportedEncodingException {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return new ModelAndView("redirect:/cart");
        }

        Order order = orderService.createOrder(customerName, shippingAddress, phoneNumber, email, notes, paymentMethod, cartItems);

        // Thực hiện thanh toán VNPay nếu phương thức thanh toán là "vnpay"
        if ("vnpay".equalsIgnoreCase(paymentMethod)) {
            // Chuẩn bị thông tin để tạo URL thanh toán VNPay
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";
            double totalAmountDouble = order.getTotalAmount();
            long amount = (long) (totalAmountDouble * 100);
//            long amount = order.getTotalAmount(); // Số tiền thanh toán
//            long amount = 10000*100;
            String bankCode = "NCB"; // Mã ngân hàng

            String vnp_TxnRef = Config.getRandomNumber(8); // Mã giao dịch duy nhất
            String vnp_IpAddr = "127.0.0.1"; // Địa chỉ IP của khách hàng

            String vnp_TmnCode = Config.vnp_TmnCode; // Mã merchant của bạn

            // Tạo các tham số cho URL thanh toán
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND"); // Mã tiền tệ

//            vnp_Params.put("vnp_BankCode", bankCode);
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef); // Thông tin đơn hàng
            vnp_Params.put("vnp_OrderType", orderType);

            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl); // URL để VNPay chuyển hướng sau khi thanh toán
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            // Sắp xếp tham số theo thứ tự alphabet
            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator<String> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if (fieldValue != null && fieldValue.length() > 0) {
                    // Xây dựng dữ liệu hash
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    // Xây dựng query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }

            String queryUrl = query.toString();
            String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

            // Chuyển hướng đến VNPay để thanh toán
            return new ModelAndView("redirect:" + paymentUrl);
        }

        return new ModelAndView("redirect:/order/confirmation"); // Nếu không phải thanh toán vnpay, chuyển hướng đến trang xác nhận đơn hàng

    }
    @GetMapping("/vnpay_return")
    public String vnpayReturn(@RequestParam Map<String, String> params, Model model) {
        // Xử lý kết quả trả về từ VNPay
        String vnp_SecureHash = params.get("vnp_SecureHash");
        if (params.containsKey("vnp_SecureHashType")) {
            params.remove("vnp_SecureHashType");
        }
        if (params.containsKey("vnp_SecureHash")) {
            params.remove("vnp_SecureHash");
        }

        String signValue = vnpayService.hashAllFields(params);
        if (signValue.equals(vnp_SecureHash)) {
            String responseCode = params.get("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                // Xử lý thành công
                model.addAttribute("message", "Thanh toán thành công!");
                return "cart/order-confirmation";
            } else {
                // Xử lý thất bại
                model.addAttribute("message", "Thanh toán thất bại.");
                return "cart/order-confirmation";
            }
        } else {
            // Xử lý lỗi xác thực
            model.addAttribute("message", "Bạn đã thanh toán thành công");
            return "cart/order-confirmation";
        }
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Your order has been successfully placed.");
        return "cart/order-confirmation";
    }

}

