# ==========================================
# Giai đoạn 1: Build source code bằng Maven
# ==========================================
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

# Copy file cấu hình maven và source code
COPY pom.xml .
COPY src ./src

# Tiến hành build (bỏ qua Test để nhanh hơn)
RUN mvn clean install -DskipTests

# ==========================================
# Giai đoạn 2: Tạo Image chạy Runtime
# ==========================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Cài đặt Git để tải Uesugi-Resources
RUN apk add --no-cache git

# Copy file jar đã được build từ Giai đoạn 1 sang
COPY --from=builder /build/UesugiServer.jar /app/

# Copy script khởi động
COPY entrypoint.sh /app/
RUN chmod +x /app/entrypoint.sh

# Mở port server
EXPOSE 8080

# Chạy script khởi động
ENTRYPOINT ["/app/entrypoint.sh"]
