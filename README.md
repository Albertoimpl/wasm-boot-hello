# Spring boot and WebAssembly

Calling a simple sum function written in C from a .wasm file from a Spring Boot application using [wasmer-java](https://github.com/wasmerio/wasmer-java).

To run the project:
```shell
./gradlew bootRun
```

To call the function:
```shell
curl 'http://localhost:8080/sum?x=10&y=25'
```


Note that this needed to be added in `build.gradle`
```groovy
bootRun {
    jvmArgs = ["-Dos.arch=amd64"]
}
```

To compile the sum function

```shell
git clone https://github.com/emscripten-core/emsdk.git
cd emsdk
./emsdk install latest
./emsdk activate latest
source ./emsdk_env.sh
```

```shell
emcc sum/sum.c -o sum/sum.js -s EXPORTED_FUNCTIONS='["_sum"]'
```

```shell
cp sum/sum.wasm src/main/resources/
```
