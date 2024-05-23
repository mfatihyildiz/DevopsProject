# Java 17 Alpine JDK kullanarak base image seçimi
FROM openjdk:17-alpine

# Uygulama dosyalarını konteynere kopyalamak için çalışma dizini belirleme
WORKDIR /app

# Yerel build işleminden sonra oluşan executable jar dosyasını kopyalama
COPY build/libs/*.jar app.jar

# Uygulamayı başlatmak için gerekli komut
CMD ["java", "-jar", "app.jar"]
