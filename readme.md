# Api Spec

## Request Code OTP

Request :

- Method : GET
- Endpoint: `api/otp/${phone}`
- Header:
    - Accept: application/json
- Response :

```json
{
  "success": "bool",
  "message": "String",
  "phone": "String"
}
```

## Validation Code OTP

Request :

- Method : GET
- Endpoint: `api/otp/${phone}/${code}`
- Header:
    - Accept: application/json
- Response :

```json
{
  "success": "bool",
  "message": "String",
  "phone": "String"
}
```