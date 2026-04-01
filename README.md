# Not Enough Object Info — v2.0.0

**A Minecraft 1.20.1 Forge mod** — real-time information about blocks, entities, fluids, biomes, items and your own player stats, displayed directly on your HUD and in item tooltips.




Current Status
[![neoi](https://modfolio.creeperkatze.de/modrinth/project/neoi/downloads)](https://modrinth.com/project/neoi) 
[![NotEnoughObjectInfo (NEOI)](https://modfolio.creeperkatze.de/curseforge/project/1500541)](https://www.curseforge.com/minecraft/mc-mods/neoi)


---

## ✦ What's New in v2

| Feature | v1 | v2 |
|---|---|---|
| Block info panel | ✅ | ✅ + push reaction, emit light |
| Entity info panel | ✅ | ✅ + UUID, atk speed, follow range, KB res, baby flag, invisible |
| Fluid info panel | data only | ✅ **Fully rendered** |
| Biome info panel | ❌ | ✅ **New** |
| Coordinate overlay | ❌ | ✅ **New** (chunk, region, portal calc) |
| Player stats panel | ❌ | ✅ **New** |
| Item held — enchants | ❌ | ✅ **New** |
| Tooltip — harvest tier | ❌ | ✅ **New** |
| Config GUI tabs | ❌ | ✅ **9 tabs** |
| Keybindings | 3 | **8** |

---

## ⌨ Keybindings (v2)

| Key | Action |
|---|---|
| **H** | Toggle HUD on/off |
| **J** | Cycle COMPACT → NORMAL → VERBOSE |
| **N** | Toggle NBT display |
| **B** | Toggle Biome panel |
| **C** | Toggle Coordinate overlay |
| **P** | Toggle Player stats panel |
| **F** | Toggle Fluid panel |
| *(unbound)* | Cycle config preset |

All keys rebindable under **Options → Controls → Not Enough Object Info**.

---

## 📐 Panels

```
┌─────────────────────┐  ← Block Info (gold header)
│ ✦ BLOCK INFO        │
│ Name: Stone         │
│ ID: minecraft:stone │
│ Hardness: 1.5 ...   │
└─────────────────────┘
┌─────────────────────┐  ← Entity Info (teal header)
│ ✦ ENTITY INFO       │
│ Name: Zombie        │
│ Health: 20.0 / 20.0 │
└─────────────────────┘
┌─────────────────────┐  ← Held Item (yellow header)
│ ✦ HELD: Iron Sword  │
│ ID: minecraft:...   │
│ Durability: 251/251 │
└─────────────────────┘
┌─────────────────────┐  ← Biome (purple) — key B
│ ✦ BIOME INFO        │
│ Name: Plains        │
│ Temp: Temperate     │
└─────────────────────┘
```

**Coordinate overlay** (key C) sits at a separate configurable position.  
**Player stats panel** (key P) appears to the right of the main HUD column.

---

### 💻Developer Stuff

## 🔨 Building


Requires **JDK 17** and an internet connection to download Forge MDK.

**Build System:**  
The mod uses Gradle with ForgeGradle 6.x. The `build.gradle` includes a `processResources` task that automatically replaces placeholders in `mods.toml` (like `${mod_id}`, `${mod_version}`) with values from `gradle.properties`, ensuring the JAR has a valid modId recognized by Forge.

---

## 📦 Installation

1. Install **Forge 1.20.1-47.2.0** or later
2. Build the mod: `./gradlew build`
3. Take `NotEnoughObjectInfo-2.0.0.jar` from `build/libs/`
4. Drop it into `.minecraft/mods/`
5. Launch Forge 1.20.1 — the mod will appear in the modlist ✅

The mod includes comprehensive configuration through an in-game config screen accessible via **Mods → Not Enough Object Info → Config**.

---

## 🐛 API Compatibility Notes (v2.0.0)

This version was updated to be fully compatible with **Minecraft 1.20.1 + Forge 47.2.0**. Key API changes addressed:

- **Entity.isOnGround()** — This method is private; use NBT data or check velocity instead
- **FluidType.isGaseous()** — Method removed; fluid properties are determined via FluidType attributes
- **Block.getSpeedFactor() / getJumpFactor()** — Changed to no-argument methods (no BlockState parameter)
- **Biome.climateSettings** — Access via `biome.getModifiedClimateSettings().downfall()` 
- **KeyMapping unassigned keys** — Use `-1` as the key code instead of `KEY_UNKNOWN`

All these changes have been integrated into the codebase.

---

## 📜 License

**Copyright © 2026 EmanuelPlays**  
Licensed under **CC BY-ND 4.0** — you may redistribute and include in modpacks with credit, but may not modify or create derivative works without explicit permission.  
→ https://creativecommons.org/licenses/by-nd/4.0
