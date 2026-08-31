package com.sosha.python;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class PythonManager {
    private Process pythonProcess;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void start() {
        try {
            String pythonCmd = System.getProperty("os.name").toLowerCase().contains("windows") ? "python.exe" : "python";
            ProcessBuilder pb = new ProcessBuilder(pythonCmd, "python-sidecar/app/main.py");
            pb.directory(new java.io.File(".").getAbsoluteFile().getParentFile());
            pythonProcess = pb.start();
            scheduler.scheduleAtFixedRate(this::checkHealth, 2, 5, TimeUnit.SECONDS);
            System.out.println("Python sidecar started");
        } catch (IOException e) {
            System.err.println("Failed to start Python sidecar: " + e.getMessage());
        }
    }

    public void stop() {
        if (pythonProcess != null && pythonProcess.isAlive()) {
            pythonProcess.destroyForcibly();
        }
        scheduler.shutdown();
    }

    private void checkHealth() {
        // stub health check - in production would call /health endpoint
    }
}
