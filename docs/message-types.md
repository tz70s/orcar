# The Message Format

The communication between different nodes is based on tcp(currently), this file discribe the communication mailBox format.

## Message Header

The mailBox header should records the following information.
To avoid heavy weight mailBox load, will used bytes to represented. (may be scale in the future)

1. `mailBox type` - 1 byte
2. `src-node-id` - 1byte
3. `src-orchestrator-id` - 1byte
4. `dst-node-id` - 1byte
5. `dst-orchestrator-id` - 1byte
6. `reserved` - 1byte
7. `\r`
8. `\n`


## Variable Header

A String based header, for storing class names.(currently)

1. Class Name + `\r\n\r\n` (CRLF)

## Message Payload 

String
