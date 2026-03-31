# Not Enough Object Info (NEOI)

**A Minecraft 1.20.1 Forge mod** that displays detailed, real-time information about blocks, entities, and items directly on your HUD and in item tooltips.

---

## ✦ Features

### HUD Overlay (look at something to see its info)
| Category | Information Shown |
|---|---|
| **Blocks** | Name, Registry ID, Mod, Hardness, Blast Resistance, Harvest Tool, Block/Sky Light, Redstone Power, Flammability, Friction, Block State Properties, Tags, Block Entity type + NBT |
| **Entities** | Name, Registry ID, Mod, Category, Health bar (colour-coded), Max Health, Armor, Movement Speed, Attack Damage, Active Potion Effects with duration, Position, Dimension, Tags, NBT |
| **Held Item** | Registry ID, Mod, Durability bar (colour-coded), Enchantability, Burn Time, Food Value + Saturation, Max Stack Size, Tags |

### Item Tooltips
Every item tooltip gains:
- Registry ID & source Mod
- Remaining Durability (%)
- Enchantability
- Max Stack Size
- Burn Time (ticks + seconds)
- Food Value & Saturation
- Repair ingredient info
- NBT summary
- Item tags

---

## ⌨ Keybindings

| Key | Action |
|---|---|
| **H** | Toggle HUD on/off |
| **J** | Cycle display mode (COMPACT → NORMAL → VERBOSE) |
| **N** | Toggle NBT display |

All keybindings can be rebound in **Options → Controls → Not Enough Object Info**.

---

## 📖 Display Modes

- **COMPACT** — Name, ID, Mod, and a few key stats only.
- **NORMAL** *(default)* — Full standard info without the most verbose fields.
- **VERBOSE** — Everything: state properties, tags, NBT, all attributes.

---

## ⚙ Configuration

Open the settings screen via:
- **Mod Menu** → NEOI → Config button
- Or edit `config/neoi-client.toml` in your Minecraft instance folder

All ~50 individual display toggles are configurable, as well as:
- HUD position (X/Y)
- Text scale (25–200%)
- Background transparency
- Text shadow on/off

---

## 📦 Installation

1. Install **Minecraft Forge 1.20.1-47.2.0** (or newer 47.x)
2. Drop `NotEnoughObjectInfo-1.0.0.jar` into your `mods/` folder
3. Launch the game — NEOI is ready!

---

## 📝 License

Copyright © 2026 EmanuelPlays

This work is licensed under the Creative Commons Attribution–NoDerivatives 4.0 International License (CC BY-ND 4.0).

You are free to:
- Use, share, and redistribute this mod in any format
- Include it in modpacks or showcase it publicly
- Credit the author (EmanuelPlays) when sharing

You may NOT:
- Modify, edit, translate, or create derivative works based on this mod
- Repackage or rebrand without explicit permission

To request permission for modifications or derivative use, contact the author directly.

License details: [https://creativecommons.org/licenses/by-nd/4.0](https://creativecommons.org/licenses/by-nd/4.0)
