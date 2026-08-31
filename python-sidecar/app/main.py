from fastapi import FastAPI
import uvicorn

app = FastAPI(title="Sosha AI Sidecar")

@app.get("/health")
def health():
    return {"status": "ok", "modelLoaded": True}

if __name__ == "__main__":
    uvicorn.run(app, host="127.0.0.1", port=8001, log_level="warning")
