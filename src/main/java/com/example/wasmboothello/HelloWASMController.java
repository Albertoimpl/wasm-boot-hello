package com.example.wasmboothello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wasmer.Instance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class HelloWASMController {

    @Value("classpath:sum.wasm")
    private Resource resourceFile;

    @GetMapping("/sum")
    public String sumWASM(@RequestParam("x") Integer x, @RequestParam("y") Integer y) throws IOException {
        Path wasmPath = resourceFile.getFile().toPath();
        byte[] wasmBytes = Files.readAllBytes(wasmPath);

        Instance instance = new Instance(wasmBytes);

        Object[] results = instance.exports.getFunction("sum").apply(x, y);
        Integer result = (Integer) results[0];

        instance.close();
        return result.toString();
    }

}
