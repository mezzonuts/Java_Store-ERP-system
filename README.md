# Sosha POS & Inventory Management System v2.0

> **Java 21 + JavaFX + Spring Boot + Python AI Sidecar**  
> **Dual-DB: SQLite (Lite) / PostgreSQL (Enterprise)**  
> **Offline-First Desktop + Store Cloud Optional**

---

## 🚀 Overview

Sosha v2 is a **privacy-first, offline-resilient ERP** for retail and distribution businesses. All critical operations (POS, Inventory, Finance, HR) run **100% offline** on desktop. Store Online (e-commerce) is optional and syncs selectively.

### Key Features

| Feature | Description |
| :--- | :--- |
| **Desktop Native** | JavaFX 21 + Spring Boot Embedded (no browser) |
| **Offline First** | 100% functional 30 days without internet |
| **Privacy Guaranteed** | `sync_policy=PRIVATE` never syncs (Finance, HR, Supplier Cost) |
| **Dual-DB** | SQLite (2-4GB RAM) or PostgreSQL (>4GB RAM) |
| **Local AI** | Python FastAPI sidecar (Prophet forecast, Anomaly detection) |
| **Store Cloud** | Optional Spring Boot Docker (katalog publish, order online) |
| **Hardware** | ESC/POS, HID scanner, label printer native support |

---

## 🛠 Tech Stack

### Desktop (Offline)
- **Runtime:** Java 21 LTS (OpenJDK Temurin)
- **UI:** JavaFX 21 + FXML + CSS
- **Framework:** Spring Boot 3.3 Embedded
- **ORM:** Spring Data JPA + Hibernate 6
- **DB:** SQLite 3.44 (WAL+SQLCipher) / PostgreSQL 16
- **Auth:** BCrypt + JWT (8h expiry)
- **Build:** Maven + jlink + jpackage

### Python Sidecar (Local AI)
- **API:** FastAPI + Uvicorn (localhost:8001)
- **Models:** Prophet, Scikit-learn IsolationForest, sentence-transformers

### Store Cloud (Optional)
- **Backend:** Spring Boot Docker
- **DB:** PostgreSQL Cloud
- **API:** REST JSON

---

## 📦 Installation

### System Requirements
- **Java 21 LTS** (Temurin recommended)
- **Python 3.11+**
- **RAM:** 2GB (SQLite) / 4GB+ (PostgreSQL)
- **OS:** Windows 10/11, Ubuntu 22+, macOS 13+

### Windows (MSI Installer)
```bash
# Download from Releases
sosha-2.0.0-windows-x64.msi
```

### Ubuntu (DEB)
```bash
sudo dpkg -i sosha-2.0.0-linux-amd64.deb
```

### macOS (DMG)
```bash
# Mount and drag to Applications
sosha-2.0.0-macos-x64.dmg
```

### Dual-DB Selection
Install wizard auto-detects RAM and recommends:
- **SQLite** → RAM < 4GB or < 100k SKU
- **PostgreSQL** → RAM ≥ 4GB or > 100k SKU

---

## 🏗️ Build from Source

```bash
git clone https://github.com/mezzonuts/Java_Store-ERP-system.git
cd Java_Store-ERP-system

# Compile (both DB profiles tested)
mvn clean compile -Psqlite
mvn clean compile -Ppostgres

# Package (creates JAR + jpackage installer)
mvn package

# Run tests
mvn test
```

---

## 📊 Project Structure

```
Java_Store-ERP-system/
├── desktop/                    # JavaFX + Spring Boot (Offline)
│   ├── src/main/java/com/sosha/
│   │   ├── ui/                 # JavaFX Controllers (FXML)
│   │   ├── core/               # Domain + Services
│   │   ├── security/           # Auth + RBAC
│   │   ├── sync/               # Outbox + Store Cloud
│   │   ├── python/             # Sidecar integration
│   │   └── config/             # Dual-DB + JPA
│   └── pom.xml
├── python-sidecar/             # FastAPI AI Service
│   ├── app/main.py
│   ├── forecast/
│   ├── anomaly/
│   └── rag/
├── store-cloud/                # Optional Spring Boot Docker
├── scripts/
│   └── bundle_models.py        # Download AI models
├── Plan_code_execute/          # Session plans (00-09)
├── Report_code_execute/        # Session reports
├── Product doc/                # 10 Volume documentation
└── tests/k6/                   # Load testing (k6)
```

---

## 🔐 Security

| Layer | Implementation |
| :--- | :--- |
| **Authentication** | BCrypt (cost 10), JWT HS256 (8h expiry), OS Keychain secret storage |
| **Authorization** | RBAC (`ADMIN/MANAGER/CASHIER/WAREHOUSE`) + Hibernate TenantFilter |
| **Encryption (At Rest)** | SQLite: SQLCipher AES-256-GCM; PostgreSQL: pgcrypto + FS encrypt |
| **Privacy** | `sync_policy='PRIVATE'` flag → never sync (enforced via annotation + DB trigger) |

---

## 📈 Performance

| Metric | SQLite Mode | PostgreSQL Mode |
| :--- | :--- | :--- |
| POS Checkout | <50ms | <50ms |
| FTS Search | <100ms | <100ms |
| Concurrent Users | 1-3 | 10+ |
| Startup | <3s | <5s |
| Max SKU | 100k | 5M+ |

---

## 📚 Documentation

| Volume | Topic |
| :--- | :--- |
| 0 | Product Blueprint (Architecture Overview) |
| 1 | Business Requirements (BRD) |
| 2 | Software Requirements (SRS) |
| 3 | System Architecture (Dual-DB, Offline) |
| 4 | Database Design (SQLite/PostgreSQL migrations) |
| 5 | API Documentation (Local REST + Python sidecar) |
| 6 | Frontend (JavaFX Desktop) |
| 7 | Backend (Spring Embedded + Python FastAPI) |
| 8 | Deployment (jpackage, CI/CD) |
| 9 | Testing (JUnit5, pytest, k6) |
| 10 | AI (Local Forecast, Anomaly, RAG) |

---

## 🤝 Contributing

1. Fork the repo
2. Create feature branch (`git checkout -b feat/my-feature`)
3. Commit (`git commit -m 'feat: add my feature'`)
4. Push (`git push origin feat/my-feature`)
5. Open Pull Request

---

## 📝 License

MIT License - See [LICENSE](LICENSE) for details.

---

## 🙏 Acknowledgments

- **JavaFX** for enterprise desktop UI
- **Spring Boot** for embedded framework
- **FastAPI** for Python AI sidecar
- **SQLite & PostgreSQL** for reliable local storage
- All contributors and users

---

**Ready for Production** 🚀  
*Last updated: August 31, 2026*
