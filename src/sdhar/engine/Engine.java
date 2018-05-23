package sdhar.engine;

import java.io.*;
import java.util.function.Consumer;

public class Engine implements AutoCloseable {

    private final ProcessBuilder builder;
    private Process process;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader reader;


    public Engine(final String path) {
        builder = new ProcessBuilder(path);
    }

    public void openProgram() throws IOException {
        process = builder.start();
        inputStream = process.getInputStream();
        outputStream = process.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public boolean sendUciCommand() throws IOException {
        if (outputStream == null || reader == null)
            throw new IOException("Something went wrong in trying to send `uci` command" +
                    "to engine.\n" +
                    "The outputstream or reader is null.");


        outputStream.write("uci\n".getBytes());
        outputStream.flush();

        String line = reader.readLine();
        boolean found = false;

        while ((line = reader.readLine()) != null) {
            if (!found) {
                if (line.contains("id")) {
                    found = true;
                } else {
                    break;
                }
            }
            if (line.contains("uciok")) break;
        }

        return found;
    }

    public void sendUciNewGameCommand() throws IOException {
        if (outputStream == null)
            throw new IOException("Something went wrong in trying to send `ucinewgame` command" +
                    "to engine.\n" +
                    "The outputstream is null.");

        outputStream.write("ucinewgame\n".getBytes());
        outputStream.flush();
    }

    public void sendGoCommand(
            final int depth,
            final Consumer<EvaluationOutput> func
    ) throws IOException {
        if (outputStream == null)
            throw new IOException("Something went wrong in trying to send `go` command" +
                    "to engine.\n" +
                    "The outputstream is null.");
        outputStream.write(("go depth " + depth + "\n").getBytes());
        outputStream.flush();

        final Thread thread = new Thread(() -> {
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    final String[] parts = line.trim().split(" ");
                    final String tag = parts[0].trim();

                    int currentDepth = 0;
                    int score = 0;
                    String bestMove = null;

                    for (int i = 0; i < parts.length; i++) {
                        final String item = parts[i];
                        switch (item.trim().toLowerCase()) {
                            case "depth":
                                currentDepth = Integer.parseInt(parts[i+1]);
                                break;
                            case "score":
                                score = Integer.parseInt(parts[i+1]);
                                break;
                            case "bestmove":
                                bestMove = parts[i+1];
                                break;
                        }
                    }

                    EvaluationOutput out = new EvaluationOutput(currentDepth, score);
                    out.setBestMove(bestMove);
                    func.accept(out);

                    if (tag.toLowerCase().equals("bestmove")) break;
                }
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        });
        thread.start();
    }

    public void sendPositionCommand(final String fen) throws IOException {
        if (outputStream == null)
            throw new IOException("Something went wrong in trying to send `position` command" +
                    "to engine.\n" +
                    "The outputstream is null.");

        outputStream.write((fen + "\n").getBytes());
        outputStream.flush();
    }

    public void sendStopCommand() throws IOException {
        if (outputStream == null)
            throw new IOException("Something went wrong in trying to send `stop` command" +
                    "to engine.\n" +
                    "The outputstream is null.");
        outputStream.write("stop".getBytes());
        outputStream.flush();
    }

    @Override
    public void close() {
        if (process != null && process.isAlive()) {
            if (outputStream != null) {
                try {
                    outputStream.write("quit".getBytes());
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            process.destroy();
        }
    }
}
