# SESSION 08 - Python Sidecar (Local AI)

## Tujuan
Forecast, anomaly, RAG 100% lokal tanpa cloud, bundled.

## Scope
- `python-sidecar` FastAPI `:8001` + `Java PythonManager` watchdog + `PythonClient`
- Forecast Prophet, Anomaly IsolationForest, Vector Chroma

## Task
1. `python-sidecar/app/main.py`: `/health`, `/api/v1/forecast`, `/api/v1/anomaly/check`, `/api/v1/rag/query`
2. `forecast/prophet_service.py` (Prophet + fallback ExponentialSmoothing)
3. `anomaly/isolation.py` (IsolationForest)
4. `rag/local_vector.py` (Chroma SQLite `~/.sosha/vectors.db` + sentence-transformers `all-MiniLM-L6-v2`)
5. Java: `PythonManager` ProcessBuilder start `python/app/main.py` + watchdog thread (restart if exit), `PythonClient` Retrofit timeout 2s fallback
6. UI: `ForecastChart` LineChart, `RecommendationPane` di POS
7. Bundling: `scripts/download_models.py` + `resources/python/` zip

## Deliverable
- `GET :8001/health` ok, forecast 30d <2s
- Java checkout triggers anomaly async no block
- Offline NLQ "stok menipis" -> SQL `SELECT * FROM v_low_stock`

## File
- `python-sidecar/app/main.py`, `forecast/*`, `anomaly/*`, `rag/*`, `core/python/PythonManager.java`, `PythonClient.java`

## Kriteria
- Sidecar startup 2s, RAM 300MB
- Forecast MAPE <0.2 on sample
- No data leaves PC (Wireshark audit)

## Estimasi: 6 hari
