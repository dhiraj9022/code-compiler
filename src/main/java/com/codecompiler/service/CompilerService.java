package com.codecompiler.service;

import com.codecompiler.dto.CodeRequest;
import com.codecompiler.dto.CodeResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class CompilerService {

    public CodeResponse compileAndRun(CodeRequest codeRequest) {
        CodeResponse response = new CodeResponse();
        String language = codeRequest.getLanguage();
        String code = codeRequest.getCode();

        try {
            String fileName = "Main";
            String extension = "";

            switch (language.toLowerCase()) {
                case "java":
                    fileName += ".java";
                    extension = ".java";
                    break;
                case "go":
                    fileName += ".go";
                    extension = ".go";
                    break;
                case "php":
                    fileName += ".php";
                    extension = ".php";
                    break;
                case "js":
                    fileName += ".js";
                    extension = ".js";
                    break;
                case "python":
                    fileName += ".py";
                    extension = ".py";
                    break;
                case "c++":
                    fileName += ".cpp";
                    extension = ".cpp";
                    break;
                default:
                    response.setStatus("Error");
                    response.setOutput("Unsupported language");
                    return response;
            }

            Files.write(Paths.get(fileName), code.getBytes());
            Process process;
            if (language.equalsIgnoreCase("java")) {
                process = new ProcessBuilder("javac", fileName).start();
                process.waitFor();
                process = new ProcessBuilder("java", "Main").start();
            } else if (language.equalsIgnoreCase("go")) {
                process = new ProcessBuilder("go", "run", fileName).start();
            } else if (language.equalsIgnoreCase("php")) {
                process = new ProcessBuilder("php", fileName).start();
            } else if (language.equalsIgnoreCase("js")) {
                process = new ProcessBuilder("node", fileName).start();
            } else if (language.equalsIgnoreCase("python")) {
                process = new ProcessBuilder("python", fileName).start();
            } else if (language.equalsIgnoreCase("c++")) {
                process = new ProcessBuilder("g++", fileName, "-o", "Main").start();
                process.waitFor();
                process = new ProcessBuilder("./Main").start();
            } else {
                response.setStatus("Error");
                response.setOutput("Unsupported language");
                return response;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            response.setOutput(output.toString());
            response.setStatus("Success");

        } catch (Exception e) {
            response.setStatus("Error");
            response.setOutput(e.getMessage());
        }
        return response;
    }
}
