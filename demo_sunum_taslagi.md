# Sunum Taslağı: Demo Testleri

Bu belge, sunum sırasında kullanabileceğiniz test senaryolarının özetini ve akışını içerir. Demo klasöründeki her bir dosya için test kapsamı ve beklenen davranışlar aşağıda listelenmiştir.

---

## 1. US04: Arama Fonksiyonu Testleri (`US04_AramaTesti.java`)

**Amaç:** Kullanıcıların (Admin, User, Ziyaretçi) site içi arama motorunu kullanarak hizmet, ilaç veya aşıları bulabildiğini doğrulamak.

*   **TC01: Admin Arama (Pozitif):** Admin girişi yapıldıktan sonra geçerli bir kelime ("Distemper") ile arama yapılır ve sonuçların listelendiği doğrulanır.
*   **TC02: Admin Kısmi Arama:** Kelimenin sadece bir kısmı ("Dis") girilerek arama yapılır ve ilgili sonuçların geldiği kontrol edilir.
*   **TC03: Admin Geçersiz Arama:** Sistemde olmayan bir kelime ("Nutella") ile arama yapılır, sonuç bulunamadığı durum test edilir.
*   **TC04: Admin Boş Arama:** Arama kutusu boş bırakılarak arama yapılır, tüm listeyi getirip getirmediği kontrol edilir.
*   **TC05: Arama Sonucu Detayına Gitme:** Arama sonuçlarının birine tıklanarak detay sayfasına yönlendirme kontrol edilir.
*   **TC06: Performans Testi:** Arama sonuçlarının 2 saniye içinde yüklenip yüklenmediği test edilir.
*   **User & Ziyaretçi Testleri:** Benzer senaryolar User ve Ziyaretçi rolleri için de tekrarlanır.

---

## 2. US11: Departman ve Randevu Testleri (`US11_DepartmanErismeVeRandevuTesti.java`)

**Amaç:** Kullanıcıların departmanları inceleyebilmesi ve randevu formu aracılığıyla randevu alabilmesini doğrulamak.

*   **TC01 & TC02: Görünürlük Kontrolü:** Ana sayfada "Departments" bölümünün, başlık ve açıklamaların görünürlüğü doğrulanır.
*   **TC03: Sayfa Yönlendirmesi:** "Departments" linkine tıklanarak ilgili sayfaya gidildiği kontrol edilir.
*   **TC04: Departman Kartları:** Departman resimlerinin yüklendiği ve her bir karta tıklandığında ilgili detay sayfasına gidildiği teyit edilir.
*   **TC05: Form İncelemesi:** Detay sayfasındaki randevu formunun tüm alanları (Etiketler, Inputlar, Dropdownlar) kontrol edilir.
*   **TC06: Randevu Oluşturma (Pozitif):** Geçerli verilerle form doldurulur ve başarılı bir şekilde randevu oluşturulduğu (Success mesajı) doğrulanır.
*   **TC07: Hata Kontrolü (Negatif):** Geçersiz tarih, telefon veya çok uzun mesaj ile form gönderilir ve hata mesajı alınıp alınmadığı kontrol edilir.
*   **TC08: Mobil Uyumluluk:** Tarayıcı mobil boyutlara (iPhone 12 Pro) getirilerek sayfa düzeni ve taşma (overflow) olup olmadığı test edilir.

---

## 3. US18: İlaç Erişimi ve Randevu Butonu (`US18_IlacErismeVeRandevuOlusturmaTesti.java`)

**Amaç:** Kullanıcıların ilaçları listeleyebilmesi, detaylarını görebilmesi ve randevu alabilmesi.

*   **TC01: Erişim Testi:** Giriş yapmış kullanıcının "Medicines" sayfasına erişebildiği doğrulanır.
*   **TC02: Liste Kontrolü:** İlaçların grid yapısında listelendiği ve ilaç sayısının doğru geldiği kontrol edilir.
*   **TC03: Temel Bilgiler:** Listede ilaç isimlerinin ve resimlerinin göründüğü doğrulanır.
*   **TC04: Detay Sayfası:** Belirli bir ilacın ("Rimadyl") detay sayfasına gidilerek başlık, açıklama ve kullanım talimatlarının içeriği kontrol edilir.
*   **TC05: Randevu Butonu BUG Kontrolü:** İlaç detay sayfasındaki randevu formunun boş gönderildiğinde bile başarı mesajı verip vermediği (Critical Bug) test edilir.

---

## 4. US31: Yönetim Paneli Doktor Yönetimi (`US31_YonetimPaneliDoktorYonetimAraclariTesti.java`)

**Amaç:** Admin kullanıcısının doktorları yönetebilmesi (Listeleme, Arama, Düzenleme, Silme).

*   **TC01: Menü Erişimi:** Admin panelinde "Doctors" menüsünün ve alt menülerinin erişilebilir olduğu doğrulanır.
*   **TC02: Listeleme ve Arama:** Doktor listesinin yüklendiği, arama kutusunun çalıştığı ("Daniel" araması) ve filtrelemenin doğru yapıldığı test edilir.
*   **TC05: Düzenleme (Edit):** Bir doktorun bilgileri düzenlenir, kaydedilir ve değişikliğin yansıdığı doğrulanır. Ayrıca zorunlu alanın (Title) boş bırakılamayacağı test edilir.
*   **TC06: Silme (Delete):** Bir doktor kaydı silinir ve listeden kaldırıldığı doğrulanır.

---

## 5. US43: Yönetici Profil Düzenleme (`US43_YoneticiProfilDuzenlemeTesti.java`)

**Amaç:** Admin kullanıcısının kendi profil bilgilerini düzenleyebilmesi.

*   **TC01: Profil Menüsü:** Admin panelinde sağ üstteki kullanıcı menüsüne tıklandığında profil seçeneklerinin açıldığı kontrol edilir.
*   **TC02: Profil Sayfası Erişimi:** "Edit Profile" butonuna tıklandığında profil düzenleme sayfasının açıldığı ve 404 hatası alınmadığı doğrulanır.

---

**Not:** Demo süresince sunumun akıcı olması için kritik adımların arasına 1-2 saniyelik `bekle()` komutları eklenmiştir. Testler çalıştırılırken bu beklemeler sayesinde işlemler izleyiciler tarafından daha rahat takip edilebilir.
