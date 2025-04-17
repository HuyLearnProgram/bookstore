# 📚 Bookstore Management System

Dự án **Bookstore Management System** là một hệ thống quản lý cửa hàng sách được xây dựng bằng Java và MySQL. Dự án hỗ trợ các chức năng như:

- Áp dụng mẫu thiết kế **Factory Method** để tách biệt các chức năng xử lý dữ liệu:
- Quản lý sách (thêm, sửa, xóa, tìm kiếm)
- Quản lý đơn hàng và đơn hàng chi tiết
- Quản lý khách hàng
- Xử lý đặt hàng, hủy đơn, giao hàng

---

- Áp dụng mẫu thiết kế **Singleton** với các chức năng:
- Quản lý giỏ hàng
- Quản lý phiên người dùng
- Kết nối Database

---

## 🛠️ Công nghệ sử dụng

- Java (JDK 20+)
- JDBC (Java Database Connectivity)
- MySQL (trên nền tảng Aiven Cloud)
- MVC Design Pattern
- Factory Method Pattern
- Singleton Pattern
- Maven (quản lý thư viện)
- JUnit (kiểm thử)

---

## 🌐 Cấu hình môi trường với `.env`

Dự án sử dụng biến môi trường để quản lý thông tin kết nối cơ sở dữ liệu. Bạn cần tạo file `.env` trong thư mục gốc của project với nội dung như sau:

```env
DB_URL
DB_USER
DB_PASSWORD
```
---

## License
Vũ Gia Huy – Học viện Công nghệ Bưu chính Viễn thông

Hồ Anh Tuấn - Học viện Công nghệ Bưu chính Viễn thông
