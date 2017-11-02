# The Message Format

The communication between different nodes is based on tcp(currently), this file discribe the communication message format.

## Message Header

The message header should records the following information.
1. `dst-node-id` or `gateway/controller`
2. `dst-orchestrator-id` or `gateway/controller`
3. `src-node-id` or `gateway/controller`
4. `src-orchestrator-id` or `gateway/controller`
3. `message type`

To avoid heavy weight message load, will used bitmap to represented.

1. `node-id` - 1byte
2. `orchestrator-id` - 1byte
3. `message type` - 1byte
4. `reserved` - 3byte
5. `payload`
