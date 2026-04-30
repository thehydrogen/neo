# neo
Neo is a barebones, basic movement emulator for Minecraft's Java Edition physics (for version 1.8)

Neo is **not** intended to be used as provided; it is a base to understand movement emulation and continue to add
support for further scenarios.

Comments have been left in places where fixes are required.

## What's missing?
- Ladders - comments have been left where they need to be implemented. Refer to MCP
- Potion effects - to be implemented in jumping and aiMoveSpeed
- Water and lava movement - to be implemented in move entity with heading. Refer to MCP
- Motion and bounding boxes for colliding blocks - look in `me.hydro.emulator.collision`. Refer to MCP
- Velocity/Knockback
- Post 1.8 clients

## Usage
Each player you're emulating should have their own `Emulator` object.

On a flying packet, call `Emulator#runIteration` with an `IterationInput` object, which can be created like so:

```java
final AxisAlignedBB lastReportedBoundingBox = new AxisAlignedBB(
    fromX - 0.3,
    fromY,
    fromZ - 0.3,
    fromX + 0.3,
    fromY + 1.8,
    fromZ + 0.3
);

final IterationInput iteration = IterationInput.builder()
    .to(new Vector(x, y, z)) // location from the flying packet
    .yaw(yaw) // player's current yaw
    .ground(onGroundBoolean)
    .jumping(jumpingBoolean) // you'll want to bruteforce this
    .forward(forward) // you'll want to bruteforce this - should be '1', '0' or '-1'
    .strafing(strafing) // you'll want to bruteforce this - should be '1', '0' or '-1'
    .sprinting(sprintingBoolean) // you'll want to bruteforce this (the sprinting state can desync)
    .usingItem(useItemBoolean) // you'll want to bruteforce this
    .hitSlowdown(hitSlowdownBoolean) // you'll want to bruteforce this
    .sneaking(sneakingBoolean)
    .lastReportedBoundingBox(lastReportedBoundingBox) // from location, as a bounding box
    .build();
```

This method returns an `IterationResult` object.

Upon finding the correct result, run `Emulator#confirm` on the `IterationHolder` received from the `IterationResult.`

A demo in code can be found in [`examples/`](https://github.com/thehydrogen/neo/tree/master/examples).

## License
Neo is licensed under the MIT License. Have fun
