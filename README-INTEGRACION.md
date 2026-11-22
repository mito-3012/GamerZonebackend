# Configuración backend y pagos

> Nota: el ZIP frontend entregado corresponde a **GamerZone** (videojuegos), no a PetCare. Se conservó su estructura y marca para no cambiar el dominio funcional del proyecto.

## Base de datos persistente
1. Instalar Docker Desktop.
2. Desde la carpeta backend ejecutar `docker compose up -d`. Esto crea MySQL 8 y un volumen persistente `petcare_data`.
3. Revisar `.env.example` y configurar `DB_USERNAME` / `DB_PASSWORD` según el entorno.
4. Ejecutar `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`).

Hibernate mantiene el esquema con `ddl-auto=update`. Para producción se recomienda migrar a Flyway y credenciales secretas.

## Swagger
Con backend iniciado: http://localhost:8080/swagger-ui.html

## Registrar un pago
El pedido debe existir previamente en `pedidos` y tener `total` calculado.
`POST /api/pagos`:
```json
{
  "idPedido": 1,
  "metodoPago": "TRANSFERENCIA",
  "monto": 59990,
  "observacion": "Pago de prueba"
}
```
El backend comprueba que el pedido exista y que el monto coincida con su total. La respuesta incluye el ID de pago, estado, moneda y referencia. El estado queda APROBADO en modo demostración y el pedido cambia a PAGADO.

## Frontend
En el frontend copiar `.env.example` a `.env` y ajustar `VITE_API_URL`. Ejecutar `npm install` y `npm run dev`. La ruta `/pago` permite registrar el pago asociado a un pedido.

## Limitación importante
La aprobación implementada es una **simulación académica**: no conecta con Transbank, Mercado Pago ni bancos y no procesa dinero real. Antes de producción se debe integrar una pasarela, validar su notificación/firma y nunca recibir ni almacenar datos de tarjetas en esta aplicación.

## Requisitos de la pauta aún por completar
La pauta exige JWT y roles, persistencia segura de sesión y restricciones por rol en frontend. Estos ZIP originales no contienen un flujo JWT completo; esta entrega no afirma que esos puntos estén resueltos. También conviene añadir pruebas de integración y proteger los endpoints administrativos antes de exponer el sistema.
