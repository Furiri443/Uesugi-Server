#!/bin/sh

# Tự động tải resource nếu chưa có
if [ ! -d "/app/gotopazu/master" ]; then
    echo "[Init] gotopazu/master không tồn tại. Tiến hành clone từ Uesugi-Resources..."
    mkdir -p /app/gotopazu
    git clone -q --depth 1 https://github.com/Furiri443/Uesugi-Resources.git /tmp/Uesugi-Resources
    
    echo "[Init] Đang copy resources vào /app/gotopazu..."
    cp -r /tmp/Uesugi-Resources/* /app/gotopazu/
    rm -rf /tmp/Uesugi-Resources
    echo "[Init] Cài đặt Resource thành công!"
else
    echo "[Init] Đã tìm thấy resource tại /app/gotopazu/master."
fi

# Chạy server
echo "[Init] Đang khởi động UesugiServer..."
exec java -jar UesugiServer.jar
