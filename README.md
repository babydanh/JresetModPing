# JresetModPing (Jump Reset Ping Mod)

**Author:** Mac đ

## Giới thiệu (About)
Đây là một mod PvP dành cho Minecraft (Fabric). Chức năng chính của mod bao gồm:
- **Jump Reset Ping:** Tự động phát ra âm thanh "ping" báo hiệu khi bạn thực hiện thành công kỹ thuật Jump Reset trong PvP.
- **Hit Prediction (Dự đoán đòn đánh):** Dự đoán các đòn đánh sắp tới từ đối thủ dựa trên hoạt ảnh vung tay (arm swing) của họ để bạn có thể phản xạ dễ dàng hơn.

## Cách tải và cài đặt (How to install)
1. Tải bản mod mới nhất (file `.jar`) từ phần [Releases](https://github.com/babydanh/JresetModPing/releases) trên Github của dự án.
2. Đảm bảo bạn đã cài đặt [Fabric Loader](https://fabricmc.net/) và [Fabric API](https://modrinth.com/mod/fabric-api) cho đúng phiên bản Minecraft.
3. Copy file `.jar` vừa tải vào thư mục `mods` của Minecraft:
   - **Windows:** `%appdata%\.minecraft\mods`
   - **Mac:** `~/Library/Application Support/minecraft/mods`
   - **Linux:** `~/.minecraft/mods`
4. Khởi động game bằng profile Fabric và vào chiến thôi!

## Tự Build (How to build)
Nếu bạn muốn tự compile từ mã nguồn:
```bash
git clone https://github.com/babydanh/JresetModPing.git
cd JresetModPing
./gradlew build
```
File mod sau khi build thành công sẽ nằm trong thư mục `build/libs`.
