# jretry

**jretry** is a lightweight, zero-dependency Java library that provides a clean and extensible way to retry operations with fixed or exponential backoff.

---

## 🚀 Features

- ✅ Pure Java, no external dependencies
- ✅ Retry any `Runnable` or `Callable<T>`
- ✅ Fixed or exponential backoff strategies
- ✅ Customizable max attempts
- ✅ Small and production-ready

---

## 🔧 Installation

Clone the repo and install locally:

```bash
git clone https://github.com/orlandolorenzomk/jretry.git
cd jretry
mvn install
```

Or copy `Retry.java`, `RetryPolicy.java`, and `RetryException.java` directly into your project.

---

## 🧪 Usage

### Retry with fixed delay:

```java
Retry.run(() -> {
        // code that might fail
        }, Retry.withBackoff(3, 1000));
```

Retries up to 3 times, with 1000 ms between each attempt.

---

### Retry with exponential backoff:

```java
Retry.run(() -> {
        // code that might fail
        }, Retry.withExponentialBackoff(5, 500));
```

Waits: 500ms, 1000ms, 2000ms, 4000ms, 8000ms...

---

### Retry with return value:

```java
String response = Retry.run(() -> {
    return fetchSomething();
}, Retry.withBackoff(3, 500));
```

---

## 🧪 Testing

This project uses JUnit 5. To run tests:

```bash
mvn test
```

---

## 📦 Maven Dependency

For installing `jretry`:

```xml
<repositories>
    <repository>
        <id>repsy</id>
        <url>https://repo.repsy.io/mvn/orlandolorenzomk/jretry/</url>
    </repository>
</repositories>

<dependency>
    <groupId>org.kreyzon</groupId>
    <artifactId>jretry</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```


---

## 📄 License

This project is licensed under the MIT License.
