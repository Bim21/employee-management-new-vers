## ERP-PROJECT(Tool quản lý dự án)
Đây là tool quản lý dự án. Tool dùng để quản lý những dự án của công ty, thành viên tham gia dự án, tiến độ, đánh giá của dự án 
Tool được build bằng .NET và Angular. 
Tool ERP-PROJECT được quản lý và cập nhật thường xuyên bởi đội ngũ nhân viên của NCC. 

-----

### Installation
1. Cài đặt các thư viện của dự án
- [ASP.NET Core SDK](https://dotnet.microsoft.com/download) (phiên bản >= 3.1)
- [Node.js](https://nodejs.org/) (phiên bản LTS)
- [Angular CLI](https://cli.angular.io/) (cài đặt toàn cục)

2. Thiết lập Môi trường
**Sao chép dự án từ kho lưu trữ:**
```bash
$ git clone https://github.com/ncc-erp/ncc-erp-project
$ cd ncc-erp-project
```

## Chạy Backend (ASP.NET) theo cách thủ công

Nếu bạn muốn chạy backend (ASP.NET) bằng cách thủ công, bạn có thể làm theo các bước sau:

1. **Mở dự án Backend trong Visual Studio:**
   - Mở tệp solution `projectManagement.sln` trong thư mục `Backend` bằng Visual Studio.

2. **Chọn chế độ chạy:**
   - Chọn chế độ chạy (Debug hoặc Release) và môi trường (IIS Express hoặc một máy chủ cụ thể) theo nhu cầu của bạn.

3. **Chỉnh sửa cổng lắng nghe (nếu cần):**
   - Mở `launchSettings.json` trong thư mục `Backend/Properties`.
   - Tìm mục có tên tương ứng với dự án (ví dụ: `projectManagement.Web.Host`).
   - Thay đổi giá trị của `applicationUrl` để chỉ định cổng mà backend sẽ lắng nghe.

4. **Nhấn F5 (hoặc Chọn Debug > Start Debugging) để chạy dự án.**

Dự án backend sẽ được biên dịch và chạy trên môi trường bạn đã chọn. Thông tin về cổng lắng nghe sẽ xuất hiện trong cửa sổ Output trong Visual Studio.

Lưu ý rằng quy trình có thể thay đổi tùy theo phiên bản của Visual Studio và cấu hình cụ thể của dự án.
