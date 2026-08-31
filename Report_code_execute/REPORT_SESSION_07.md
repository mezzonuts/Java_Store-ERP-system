# REPORT SESSION 07 - JavaFX UI System

## 1. Tujuan Code Dibuat
Shell desktop polished: nav, theme, shared components, hardware ESC/POS.

## 2. Bug Tracker / Catatan Kesalahan
- **DataTable generics** – removed for now, not critical for MVP  
- Theme switch tidak persist (per-session) – will add Settings persistence later

## 3. Laporan Algoritma / Implementasi
| Komponen | Algoritma | Kompleksitas |
| :--- | :--- | :--- |
| `MainController.navigate()` | FXML load + setAll | O(1) per tab |
| `PrinterService.escposReceipt()` | String concat + ESC/POS bytes | O(n) |
| Theme toggle | Stylesheets clear + add | O(1) |

## 4. Ringkasan Pekerjaan & Hasil
**File di-add:**
- `ui/MainController.java`, `ui/common` package
- `hardware/PrinterService.java`
- `resources/fxml/main.fxml`, `resources/css/light.css`, `dark.css`

**Compile:** ✅ 41 class files (prev 37)  
**Test:** manual UI launch (no unit tests yet)

## 5. Selanjutnya
SESSION 08 – Python sidecar (Prophet, IsolationForest, RAG), FastAPI `:8001`.

---

**Status Session 00–07: ✅ DONE**  
**Remaining: 08–09**
