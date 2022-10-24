# neo
Neo is a barebones, basic movement emulator for Minecraft's Java Edition physics (for version 1.8)

## Disclaimer
Neo comes with absolutely no warranty or support, use at your own risk.

Neo is not intended to be used as provided, it is a base to understand movement emulation and continue to add support for
further scenarios.

Comments have been left in places where fixes are required.

***NEO IS CURRENTLY UNTESTED. EXPECT ISSUES***

## What's missing?
- Ladders - comments have been left where they need to be implemented. Refer to MCP
- Potion effects - to be implemented in jumping and aiMoveSpeed
- Water and lava movement - to be implemented in move entity with heading. Refer to MCP
- Motion and bounding boxes for colliding blocks - look in `me.hydro.emulator.collision`. Refer to MCP
- Velocity/Knockback
- Post 1.8 clients

## Usage
Each player you're emulating should have their own `Emulator` object.

On a flying packet, call `Emulator#runIteration` with an `IterationInput` object, which can be created like so;

```
final IterationInput iteration = IterationInput.builder()
    .to(...) // location from the flying packet
    .yaw(...) // current yaw
    .ground(...)
    .jumping(...) // you'll want to bruteforce this
    .forward(...) // you'll want to bruteforce this
    .strafing(...) // you'll want to bruteforce this
    .sprinting(...) // you'll want to bruteforce this
    .usingItem(...) // you'll want to bruteforce this
    .hitSlowdown(...) // you'll want to bruteforce this
    .sneaking(...)
    .motion(...) // see below
    .lastReportedBoundingBox(...) // from location, as a bounding box
    .build();
```

This method returns an `IterationResult` object. Among offset and predicted location, it will also return the predicted motion.

You can choose to use this predicted motion to be fed in next tick, or actual movement deltas (safer).

A demo in code can be found in the `com.example` package.

## License
Neo is licensed under the MIT License. Have fun