# Kế hoạch Phát triển Mod Minecraft Fabric (Tương thích Feather Client) - Phiên bản 1.21.11

## 1. Môi trường và Công cụ (Cần tải và cài đặt trước)
Để bắt đầu làm mod Fabric cho Minecraft 1.21.11, bạn cần chuẩn bị các phần mềm sau:
- **Java Development Kit (JDK) 21**: Phiên bản Java bắt buộc cho Minecraft 1.21+.
- **IDE (Môi trường phát triển)**: Antigravity
- **Fabric Template**: Bạn có thể dùng [Fabric Example Mod](https://github.com/FabricMC/fabric-example-mod) làm base template.

## 2. Các gói (Packages) và Thư viện cần thiết
Khi khởi tạo project (thường qua file `gradle.properties` và `build.gradle`), cần đảm bảo các dependency sau:
- **Minecraft**: Phiên bản `1.21.11`
- **Fabric Loader**: Phiên bản mới nhất hỗ trợ 1.21.11
- **Fabric API**: Bắt buộc để tương tác với các cơ chế cốt lõi của game.
- **Mixin**: Công cụ để chèn code (hook) trực tiếp vào mã nguồn của Minecraft (rất quan trọng để làm các mod liên quan đến chuyển động hoặc gói tin).

## 3. Sơ bộ về "Jump Reset Ping Mod"

### Khái niệm
- **Jump Reset**: Là một kỹ thuật trong Minecraft PvP (chiến đấu người với người). Khi bị đánh (nhận sát thương), người chơi lập tức nhảy lên để giảm thiểu độ giật lùi (knockback).
- **Ping/Latency**: Độ trễ mạng giữa client (máy người chơi) và server.

### Tính năng cốt lõi của Mod
1. **Đo lường Ping thực tế**: Lấy thông tin độ trễ mạng chính xác của người chơi.
2. **Nhận diện Jump Reset**: 
   - Sử dụng Mixin để theo dõi thời điểm người chơi nhận sát thương (`damage event`).
   - Theo dõi thời điểm người chơi bấm nút nhảy ngay sau đó.
3. **Phân tích và Hiển thị**:
   - Phát ra âm thanh (ping) khi thực hiện thành công một cú Jump Reset thay vì làm giao diện HUD phức tạp.
   - Tính toán thời gian phản xạ (delay) giữa lúc bị đánh và lúc nhảy, đối chiếu với mức Ping hiện tại xem pha xử lý đó có tối ưu với độ trễ mạng hay không.
4. **Tính năng Nâng cao: Dự đoán đòn đánh (Hit Prediction)**:
   - **Nhận diện Animation**: Bắt sự kiện đối thủ nhấc tay/vung tay (`EntityAnimationS2CPacket` hoặc sự kiện `swingHand` trên client).
   - **Tính toán (Raycasting)**: Kiểm tra ngay lập tức khoảng cách (tầm đánh ~3 block) và hướng nhìn của đối thủ xem có đang nhắm vào người chơi hay không.
   - **Phát Ping sớm**: Nếu thoả mãn, phát âm thanh ping ngay lập tức để người chơi nhảy trước khi sát thương thực sự được server ghi nhận, giúp bù đắp độ trễ mạng.
   - *Lưu ý*: Cần xử lý logic cẩn thận để tránh "báo động giả" khi đối thủ vung tay nhưng đánh trượt.

### Khả năng tương thích với Feather Client
Feather Client là một client tùy chỉnh cho phép cài đặt các mod Fabric. Để mod này hoạt động trơn tru trên Feather:
- Tránh can thiệp vào các hệ thống UI riêng của Feather.
- Sử dụng các API chuẩn của Fabric thay vì các cách hack/hook không chính thống.
- Đảm bảo mod chỉ chạy ở phía Client (Client-side mod), không yêu cầu server phải cài đặt mod này.

## 4. Các bước thực hiện tiếp theo
1. **Khởi tạo Project**: Clone template Fabric và cấu hình phiên bản 1.21.11.
2. **Setup Mixins**: 
   - Hook vào `ClientPlayerEntity` (để bắt sự kiện nhận sát thương và nhảy).
   - Hook vào `ClientPlayNetworkHandler` (để lấy thông tin Ping).
3. **Code Logic**: Viết code tính toán thời gian và vẽ UI lên màn hình.
4. **Build (Biên dịch)**: Chạy lệnh `./gradlew build` để ra file `.jar`.
5. **Thử nghiệm**: Bỏ file `.jar` vào thư mục `mods` của Feather Client và test trong game.


