"""
Script to download and bundle lightweight AI models for offline sidecar deployment.
Includes Prophet, Scikit-learn IsolationForest, and sentence-transformers all-MiniLM-L6-v2.
"""

import os
import sys

def bundle_models():
    print("Bundling AI models for offline sidecar...")
    
    # 1. Create models directory
    model_dir = os.path.join(os.path.dirname(__file__), "..", "resources", "models")
    os.makedirs(model_dir, exist_ok=True)
    
    # 2. Download sentence-transformers embedding model
    print("1. Downloading embeddings model (all-MiniLM-L6-v2 ~80MB)...")
    try:
        from sentence_transformers import SentenceTransformer
        model = SentenceTransformer('all-MiniLM-L6-v2')
        model.save(os.path.join(model_dir, "all-MiniLM-L6-v2"))
        print("   ✓ Embeddings model saved")
    except ImportError:
        print("   ! sentence_transformers not installed, skipping embedding download")
    except Exception as e:
        print(f"   ! Failed downloading embeddings: {e}")

    # 3. Create dummy IsolationForest model for initial boot
    print("2. Initializing IsolationForest model...")
    try:
        import joblib
        from sklearn.ensemble import IsolationForest
        import numpy as np
        
        # Train on mock normal sales data (total, items)
        mock_data = np.array([[50000, 2], [100000, 5], [25000, 1], [150000, 8]])
        clf = IsolationForest(contamination=0.1, random_state=42)
        clf.fit(mock_data)
        joblib.dump(clf, os.path.join(model_dir, "isolation_forest.joblib"))
        print("   ✓ IsolationForest initial model saved")
    except ImportError:
        print("   ! scikit-learn/joblib not installed, skipping IsolationForest save")

    print("\n✓ Model bundling completed!")

if __name__ == "__main__":
    bundle_models()
