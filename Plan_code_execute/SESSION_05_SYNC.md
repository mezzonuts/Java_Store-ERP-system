# SESSION 05 - Outbox & Store Cloud Selective Sync

## Tujuan
Sync selektif PUBLIC data ke Store Cloud saat online, PRIVATE never sync, reliable dengan retry & idempotency.

## Scope
- `SyncOutbox` entity + `OutboxEnqueuer` + `SyncScheduler` Quartz + `StoreClient` Retrofit
- Store Cloud Spring Boot Docker (PG Cloud PUBLIC only)

## Task
1. Entity `sync_outbox(id, table, row_id, op, payload_json, idempotency_key UNIQUE, created_at, synced_at, retry)`
2. `OutboxEnqueuer.enqueueIfPublic(entity)` cek `@SyncPolicy` + `is_published`
3. Trigger guard: `BEFORE INSERT` reject PRIVATE (test)
4. `SyncScheduler` Quartz 5s if `isOnline()` -> batch 50 -> POST `/store/api/v1/stock/publish` & `/catalog/publish` -> mark synced
5. Pull: `GET /store/api/v1/orders?since=lastSyncTs` -> insert local sales (PUBLIC)
6. Store Cloud: `store-cloud` module, entities PUBLIC only, `StoreCatalogController`, PG Cloud
7. Offline queue hold 10k, exponential backoff 3 retry -> dead_letter

## Deliverable
- Create PUBLIC product -> outbox 1 row -> online -> cloud has it -> synced_at not null
- PRIVATE finance -> outbox 0
- Offline 100 sales -> online -> all synced

## File
- `core/sync/SyncOutbox.java`, `OutboxEnqueuer.java`, `SyncScheduler.java`, `StoreClient.java`, `store-cloud/**`

## Kriteria
- Privacy audit 0 PRIVATE in outbox
- Idempotency replay OK
- Batch sync <2s

## Estimasi: 5 hari
