# Box Delivery Service (Spring Boot)

A RESTful API built with **Java 21** and **Spring Boot 3** that manages delivery boxes capable of carrying small items to remote locations.

Each **Box** has a limited weight capacity and battery level. The system enforces functional constraints such as preventing overloading or loading with a low battery.

---

### data.sql (preloaded sample boxes)

```sql
INSERT INTO BOX (txref, weight_limit, battery_capacity, state) VALUES
('BOX100', 400, 80, 'IDLE'),
('BOX300', 200, 20, 'IDLE');
```

> This preloads two boxes: one with 80% battery and one with 20%.

---

## Build & Run instructions 

```
# Build
mvn clean package

# Run
java -jar target/box-delivery-0.0.1-SNAPSHOT.jar

# H2 console (for inspection)
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:boxdb

# Example curl
# 1) Create a box
curl -X POST -H "Content-Type: application/json" -d '{"txref":"BOX123","weightLimit":450,"batteryCapacity":90}' http://localhost:8080/api/boxes

# 2) List available boxes for loading
curl http://localhost:8080/api/boxes/available

# 3) Check battery
curl http://localhost:8080/api/boxes/BOX123/battery

# 4) Load items
curl -X POST -H "Content-Type: application/json" -d '{"items":[{"name":"widget-1","weight":100,"code":"WIDGET_1"},{"name":"widget-2","weight":200,"code":"WIDGET_2"}]}' http://localhost:8080/api/boxes/BOX123/load

# 5) Get loaded items
curl http://localhost:8080/api/boxes/BOX123/items
```

---

## Example Output (simulated run & responses)

**Curl responses**

1) GET available

```
$ curl -s http://localhost:8080/api/boxes/available | jq
[
  {
    "id": 1,
    "txref": "BOX123",
    "weightLimit": 500,
    "batteryCapacity": 80,
    "state": "IDLE",
    "items": null
  }
]
```

2) GET battery

```
$ curl -s http://localhost:8080/api/boxes/BOX100/battery
80
```

3) Load items successfully

```
$ curl -s -X POST -H "Content-Type: application/json" -d '{"items":[{"name":"lens_1","weight":150,"code":"LENS_1"}]}' http://localhost:8080/api/boxes/BOX100/load | jq
{
  "id": 1,
  "txref": "BOX00000000000000001",
  "weightLimit": 500,
  "batteryCapacity": 80,
  "state": "LOADED",
  "items": null
}
```

4) Attempt to load when battery &lt; 25

```
$ curl -s -X POST -H "Content-Type: application/json" -d '{"items":[{"name":"a","weight":10,"code":"A_1"}]}' http://localhost:8080/api/boxes/BOX300/load
Cannot load: battery below 25%
```

5) Attempt to load exceeding weight

```
$ curl -s -X POST -H "Content-Type: application/json" -d '{"items":[{"name":"heavy","weight":400,"code":"HEAVY_1"},{"name":"heavy2","weight":200,"code":"HEAVY_2"}]}' http://localhost:8080/api/boxes/BOX00000000000000001/load
Cannot load: weight limit exceeded
```

6) Get items

```
$ curl -s http://localhost:8080/api/boxes/BOX100/items | jq
[
  {
    "id": 1,
    "name": "lens_1",
    "weight": 150,
    "code": "LENS_1"
  }
]
```

---

## Notes & Next steps

- Authentication/authorization not implemented (out of scope for this exercise).