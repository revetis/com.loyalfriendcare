# 🐾 LoyalFriendCare Automation Project (Team 167)

## Bu proje, LoyalFriendCare web uygulamasının test otomasyon süreçlerini uçtan uca yönetmek amacıyla geliştirilmiştir. Proje; Java 21, Selenium ve TestNG kullanılarak, sürdürülebilir Page Object Model (POM) mimarisi üzerine inşa edilmiştir.

### 🚀 Başlangıç ve Kurulum

Projeyi yerel bilgisayarınızda sorunsuz çalıştırmak için aşağıdaki adımları takip edin:

#### Projeyi Klonlayın:

git clone https://github.com/revetis/com.loyalfriendcare.git


#### JDK Kontrolü: Projenin hatasız derlenmesi için bilgisayarınızda Java 21 kurulu ve çevre değişkenlerinde (Environment Variables) tanımlı olmalıdır.

#### Bağımlılıkların Yüklenmesi: IntelliJ IDEA kullanıyorsanız sağ altta çıkan Maven bildiriminden "Load Maven Changes" butonuna tıklayarak kütüphanelerin (Selenium, TestNG, ExtentReports, JavaFaker, Apache POI) inmesini sağlayın.

#### Yapılandırma: configuration.properties dosyasını açın ve kendi ayarlarınızı ekleyin:

**Ayarlarınızı yaptıktan sonra gitignore dosyasına configurations.properties ekleyin**

* browser=chrome
* url=https://qa.loyalfriendcare.com/en
* enviroment=qa
* tester_name=Adiniz
* admin_email=size verilen admin email
* admin_password=size verilen admin sifre
* user_email=size verilen kullanici email
* user_password=size verilen kullanici sifre



### 🏗 Proje Mimarisi

Proje, sorumlulukların net bir şekilde ayrıldığı katmanlı (Layered) bir yapıdadır:

src/test/java/pages: Sadece elementlerin konumlarını (By locator) barındırır. İş mantığı (logic) içermez.

admin: Admin paneli sayfaları.

user: Kullanıcı arayüzü sayfaları.

common: Login, Header, Footer gibi ortak kullanılan bileşenler.

src/test/java/tests: Gerçek test senaryolarının (Business Logic) bulunduğu sınıflardır.

src/test/java/utilities: Projenin çekirdek mekanizmasıdır.

Driver: WebDriver yönetimi (Singleton & ThreadLocal uyumlu).

ConfigReader: .properties dosyasındaki verileri okur.

ReusableMethods: Dinamik beklemeler, ekran görüntüleri ve genel aksiyonlar.

**TestBaseRapor: Görsel raporlama desteği almak için tüm test sınıfları bu sınıfı extends etmelidir.**

### 🛠 Team Kuralları (Kritik)

Projede çakışma (conflict) yaşamamak ve kod kalitesini korumak için aşağıdaki kurallara uyulması zorunludur:

Page Sınıfı Temizliği: Page sınıflarının içine click() veya sendKeys() gibi aksiyon metodları yazmayın. Sadece locator tanımlayın.

Test Yazım Standartları: Test katmanında asla driver.findElement... kullanmayın. Bunun yerine page.click(page.locator) veya ReusableMethods içindeki metodları tercih edin.

**Raporlama Zorunluluğu: Her @Test metodunun en başında extentTest objesini başlatmayı unutmayın:**

**extentTest = extentReports.createTest("Test İsmi", "Testin Amacı");**


Git Protokolü: * Asla main branch'e doğrudan kod göndermeyin.

Kendi Branchinizde kodlarınızı gönderin

Kodunuzu push etmeden önce main'den mutlaka pull yapıp conflict kontrolü yapın.

### 📊 Raporlama ve Hata Takibi

HTML Raporları: Her koşumdan sonra test-output/ klasörü altına tarih ve saat damgalı raporlar otomatik olarak oluşturulur.

Ekran Görüntüleri: Testin başarısız olması durumunda sistem otomatik olarak ekran görüntüsü alır ve rapora gömer. Yerel kopyalar target/Screenshots/ klasöründe saklanır.

### 📦 Kullanılan Teknolojiler

Java 21: Modern dil özellikleri.

Selenium 4.39.0: Web otomasyon motoru.

TestNG: Test yönetimi, paralel koşum ve Assertion'lar.

ExtentReports 5.0.9: Zengin görsel raporlama kütüphanesi.

Apache POI: Data-Driven Testing (Excel entegrasyonu).

Team Lead: Samet (Team 167)

Scrum Master Tural (Team 167)

Destek: Proje ile ilgili tüm sorularınız için Slack kanalımızı kullanabilirsiniz.