# Hướng dẫn quản lý thông tin đăng nhập trong Auth0

## Auth0 Database
- Mặc định, thông tin người dùng được lưu trữ trong Auth0 Database
- Bao gồm: tên người dùng, email, mật khẩu (đã được mã hóa), thông tin cá nhân khác
- Truy cập quản lý người dùng: Auth0 Dashboard > User Management > Users

## Quản lý người dùng
1. Đăng nhập vào Auth0 Dashboard tại https://manage.auth0.com/
2. Chọn tenant của bạn
3. Điều hướng đến User Management > Users
4. Tại đây bạn có thể:
   - Xem danh sách người dùng
   - Thêm người dùng mới
   - Chỉnh sửa thông tin người dùng
   - Đặt lại mật khẩu
   - Xóa người dùng

## Cấu hình thêm
- **Roles**: Quản lý vai trò người dùng tại User Management > Roles
- **Permissions**: Thiết lập quyền hạn tại APIs > Your API > Permissions
```

Lưu ý: Mật khẩu người dùng không bao giờ được hiển thị dưới dạng văn bản thuần, ngay cả với admin.
