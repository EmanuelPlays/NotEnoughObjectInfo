import { useState, useEffect, useRef, useCallback } from "react";

// ─── THEME ────────────────────────────────────────────────────────────────────
const T = {
  bg:          "#0f1117",
  bgSidebar:   "#0b0e15",
  bgCard:      "#141824",
  bgHover:     "#1a2035",
  bgCode:      "#0d1220",
  bgCallout:   "#0e1a2e",
  border:      "#1e2a40",
  borderLight: "#253350",
  accent:      "#3b82f6",
  accentHover: "#60a5fa",
  accentGlow:  "rgba(59,130,246,0.15)",
  accentDim:   "rgba(59,130,246,0.08)",
  text:        "#e2e8f0",
  textMuted:   "#64748b",
  textDim:     "#334155",
  textHeading: "#f1f5f9",
  textCode:    "#93c5fd",
  success:     "#22c55e",
  warning:     "#f59e0b",
  danger:      "#ef4444",
  tip:         "#a78bfa",
};

// ─── DOCS DATA ────────────────────────────────────────────────────────────────
const DOCS = [
  {
    group: "Overview",
    pages: [
      {
        id: "introduction",
        icon: "✦",
        label: "Introduction",
        file: "introduction.md",
        badge: null,
        sections: [
          {
            title: "What is NEOI?",
            content: `**Not Enough Object Info** (NEOI) is a Minecraft 1.20.1 Forge mod that overlays real-time information about blocks, entities, and items directly on your HUD and enriches every item tooltip with extra data pulled straight from the game registry.

Whether you're a mod developer debugging block properties, a server admin investigating entity behaviour, or a player who just wants to know why that block is so slow to mine — NEOI puts the answer on screen instantly.`,
          },
          {
            title: "Key Features",
            content: `::tip
NEOI is **100% client-side**. No server-side installation required. Works on vanilla and modded servers alike.
::

- **Block HUD** — Hardness, blast resistance, harvest tool, light levels, redstone power, flammability, friction, block state, tags, and block entity NBT
- **Entity HUD** — Health bar, armor, speed, attack damage, potion effects with duration, position, dimension
- **Held Item HUD** — Registry ID, mod source, durability bar, burn time, food value, enchantability
- **Tooltip Injection** — All item metadata embedded into every inventory tooltip
- **In-Game Config GUI** — 50+ individual toggles, no file editing required
- **3 Display Modes** — Compact, Normal, Verbose, hot-swapped with a single key
- **NBT Toggle** — Instantly expose raw NBT for any block entity or item`,
          },
          {
            title: "Compatibility",
            content: `| Component | Version |
|---|---|
| Minecraft | 1.20.1 |
| Forge | 47.2.0+ |
| Java | 17+ |
| Side | Client only |

NEOI reads from Forge and Minecraft registries, so it automatically shows correct IDs and mod names for blocks and entities from **any** Forge mod — no per-mod compatibility patches needed.`,
          },
        ],
      },
      {
        id: "changelog",
        icon: "📋",
        label: "Changelog",
        file: "CHANGELOG.md",
        badge: "new",
        sections: [
          {
            title: "v1.0.0 — Initial Release",
            content: `**Released:** March 2026

**Added**
- Block info HUD with 18 toggleable fields
- Entity info HUD with 14 toggleable fields
- Held item HUD with 9 toggleable fields
- Tooltip injection system with 10 toggleable fields
- Three display modes: COMPACT / NORMAL / VERBOSE
- Keybind system: H (toggle), J (cycle mode), N (NBT toggle)
- In-game config GUI with per-field ON/OFF buttons
- ForgeConfigSpec TOML config at \`config/neoi-client.toml\`
- ModMenu config screen integration
- Action bar feedback messages for all keybind actions
- Auto-hide when F3 debug overlay is open
- Colour-coded health / durability bars (green → yellow → red)
- Block entity NBT display (compact + full)
- Potion effect display with formatted duration
- Fluid info provider (density, viscosity, temperature)`,
          },
        ],
      },
    ],
  },
  {
    group: "Setup",
    pages: [
      {
        id: "installation",
        icon: "⚡",
        label: "Installation",
        file: "installation.md",
        badge: null,
        sections: [
          {
            title: "Requirements",
            content: `Before installing, make sure you have:

| Requirement | Minimum Version |
|---|---|
| Java | 17 |
| Forge | 47.2.0 |
| Minecraft | 1.20.1 |

::warning
NEOI does **not** support Fabric or Quilt. Forge only.
::`,
          },
          {
            title: "Step-by-Step Install",
            content: `**1. Install Minecraft Forge**

Download the Forge 1.20.1 installer from [minecraftforge.net](https://minecraftforge.net) and run it:

\`\`\`bash
java -jar forge-1.20.1-47.2.0-installer.jar
\`\`\`

**2. Drop in the jar**

Place the NEOI jar into your mods folder:

\`\`\`
Windows:  %APPDATA%\\.minecraft\\mods\\
macOS:    ~/Library/Application Support/minecraft/mods/
Linux:    ~/.minecraft/mods/
\`\`\`

**3. Launch**

Select the **Forge 1.20.1** profile in the Minecraft Launcher and hit Play. NEOI activates automatically — no additional setup required.`,
          },
          {
            title: "Verifying the Install",
            content: `Join any world and look at a block. You should see a blue-tinted info panel appear in the top-left corner.

If nothing appears:
1. Press **H** — the HUD may have been toggled off
2. Check \`logs/latest.log\` for \`[NEOI]\` lines confirming the mod loaded
3. Confirm the jar filename contains \`neoi\` or \`NotEnoughObjectInfo\` and has no duplicate versions`,
          },
        ],
      },
      {
        id: "quick-start",
        icon: "🚀",
        label: "Quick Start",
        file: "quick-start.md",
        badge: null,
        sections: [
          {
            title: "Your First 60 Seconds",
            content: `::tip
Everything works out of the box. This guide helps you get comfortable fast.
::

**Look at a block** — the HUD appears top-left with name, ID, mod, hardness, tool type, and more.

**Look at a mob** — switch to entity mode automatically: health bar, armor, speed, active effects.

**Hold an item** — a second panel appears below showing registry ID, durability, and burn time.

**Hover an item in inventory** — the tooltip now includes ID, mod, durability %, enchantability, and food stats.`,
          },
          {
            title: "Essential Keybinds",
            content: `Learn these three — they cover everything:

| Key | What it does |
|---|---|
| **H** | Toggle HUD on/off |
| **J** | Cycle: COMPACT → NORMAL → VERBOSE |
| **N** | Toggle raw NBT display |

A small action bar message confirms every key press. All binds are rebindable in **Options → Controls → Not Enough Object Info**.`,
          },
          {
            title: "Changing Display Mode",
            content: `Press **J** until you find the density you want:

**COMPACT** — One line per stat, no state properties, no tags. Best for minimal screen clutter.

**NORMAL** *(default)* — Full standard data. State props, tags count, tool type, effects list.

**VERBOSE** — Everything. All tags expanded, full NBT, all entity attributes including knockback resistance and follow range.`,
          },
        ],
      },
    ],
  },
  {
    group: "Features",
    pages: [
      {
        id: "hud",
        icon: "🖥",
        label: "HUD Overlay",
        file: "hud.md",
        badge: null,
        sections: [
          {
            title: "Panel Types",
            content: `The HUD renders up to **three panels simultaneously**, each triggered by a different condition:

| Panel | Colour Accent | Trigger |
|---|---|---|
| ✦ Block Info | Gold | Crosshair on a block |
| ✦ Entity Info | Green | Crosshair on an entity |
| ✦ Held Item | Yellow | Item in main or offhand |

Panels stack vertically with a small gap between them. If no block or entity is targeted only the held item panel shows.`,
          },
          {
            title: "HUD Positioning",
            content: `Adjust position in \`neoi-client.toml\`:

\`\`\`toml
[hud_general]
  hudX = 4      # pixels from left
  hudY = 4      # pixels from top
  hudScale = 100  # 25–200 percent
\`\`\`

The panel auto-sizes to its content — no width or height to set. On a 1080p screen, \`hudX = 900\` roughly aligns to the right third.`,
          },
          {
            title: "Styling & Background",
            content: `\`\`\`toml
[hud_general]
  hudBackground      = true   # translucent backing panel
  hudBackgroundAlpha = 120    # 0 (none) – 255 (solid)
  hudShadow          = true   # drop shadow on text
\`\`\`

Each panel has a coloured header bar with an accent line underneath. Health and durability values use a live colour gradient:

- 🟢 **> 60 %** — green
- 🟡 **30–60 %** — yellow  
- 🔴 **< 30 %** — red`,
          },
          {
            title: "Auto-Hide Behaviour",
            content: `NEOI automatically hides its overlay when:

- The **F3** debug screen is open (to avoid overlap)
- \`hudEnabled = false\` in config
- The HUD has been toggled off with **H**

It reappears instantly when the condition clears — no restart needed.`,
          },
        ],
      },
      {
        id: "block-info",
        icon: "🧱",
        label: "Block Info",
        file: "block-info.md",
        badge: null,
        sections: [
          {
            title: "All Block Fields",
            content: `| Field | Config Key | Default |
|---|---|---|
| Display name | showBlockName | ✅ On |
| Registry ID | showBlockId | ✅ On |
| Source mod | showBlockMod | ✅ On |
| Hardness | showBlockHardness | ✅ On |
| Blast resistance | showBlockResistance | ✅ On |
| Harvest tool | showBlockTool | ✅ On |
| Block / sky light | showBlockLight | ✅ On |
| Redstone power | showBlockRedstone | ✅ On |
| Flammable + spread | showBlockFlammable | ✅ On |
| Friction | showBlockFriction | ✅ On |
| Block state props | showBlockState | ✅ On |
| Tags | showBlockTags | ✅ On |
| Block entity type + NBT | showTileEntityData | ✅ On |
| Replaceable | showBlockReplaceable | ❌ Off |
| Solid | showBlockSolid | ❌ Off |
| Speed factor | showBlockSpeedFactor | ❌ Off |
| Jump factor | showBlockJumpFactor | ❌ Off |
| Map color | showBlockMapColor | ❌ Off |`,
          },
          {
            title: "Hardness Values",
            content: `Hardness is shown as a float. Special cases:

| Value | Meaning |
|---|---|
| \`0.0\` | Instant break (dirt, grass) |
| \`-1.0\` | Unbreakable (bedrock, barriers) |
| \`50.0\` | Obsidian |
| \`> 50\` | Modded super-hard blocks |`,
          },
          {
            title: "Block Entity NBT",
            content: `When a block has an attached block entity, NEOI shows:

\`\`\`
Block Entity: minecraft:chest
NBT: {Items:[...], Lock:"", LootTable:"minecraft:chests/..."}
\`\`\`

The NBT is capped at 256 characters in Normal mode. Press **N** or switch to **VERBOSE** for the full output.

::tip
Block entity NBT is extremely useful for debugging spawner configurations, chest loot tables, and command block states.
::`,
          },
        ],
      },
      {
        id: "entity-info",
        icon: "🐉",
        label: "Entity Info",
        file: "entity-info.md",
        badge: null,
        sections: [
          {
            title: "All Entity Fields",
            content: `| Field | Config Key | Default |
|---|---|---|
| Display name | showEntityName | ✅ On |
| Registry ID | showEntityId | ✅ On |
| Source mod | showEntityMod | ✅ On |
| Entity category | showEntityType | ✅ On |
| Health (colour bar) | showEntityHealth | ✅ On |
| Max health | showEntityMaxHealth | ✅ On |
| Armor value | showEntityArmor | ✅ On |
| Movement speed | showEntitySpeed | ✅ On |
| Attack damage | showEntityAttackDamage | ✅ On |
| Active potion effects | showEntityEffects | ✅ On |
| World position | showEntityPosition | ✅ On |
| Dimension | showEntityDimension | ✅ On |
| Tags | showEntityTags | ✅ On |
| NBT summary | showEntityNbt | ❌ Off |`,
          },
          {
            title: "Potion Effects",
            content: `Active effects list with amplifier and formatted duration:

\`\`\`
Effects (2):
  minecraft:speed        Lv.2  1m 30s
  minecraft:regeneration Lv.1  45s
\`\`\`

Duration is formatted as **Xm Ys** for anything over a minute, or **Xs** for shorter effects.`,
          },
          {
            title: "Vehicle & Passengers",
            content: `NEOI surfaces mount relationships:

- **Passengers** — number of entities currently riding this one
- **Vehicle** — the entity this mob is riding (e.g. a horse, a boat)

Useful for debugging multi-entity stacks: boats carrying players, horses with riders, minecarts with mobs, etc.

The \`isOnFire\` state is also flagged with a 🔥 indicator when active.`,
          },
        ],
      },
      {
        id: "item-info",
        icon: "⚔️",
        label: "Items & Tooltips",
        file: "item-info.md",
        badge: null,
        sections: [
          {
            title: "Held Item HUD",
            content: `| Field | Config Key | Default |
|---|---|---|
| Registry ID | showHeldItemId | ✅ On |
| Source mod | showHeldItemMod | ✅ On |
| Durability bar | showHeldItemDurability | ✅ On |
| Enchantability | showHeldItemEnchantability | ✅ On |
| Burn time | showHeldItemBurnTime | ✅ On |
| Food value + saturation | showHeldItemFood | ✅ On |
| Max stack size | showHeldItemMaxStack | ✅ On |
| Tags | showHeldItemTags | ❌ Off |`,
          },
          {
            title: "Tooltip Extras",
            content: `NEOI appends an extra section to **every item tooltip**:

\`\`\`
Diamond Sword
  Sharpness V
  Unbreaking III

  ID: minecraft:diamond_sword
  Mod: Minecraft
  Durability: 1561 / 1561  (100%)
  Enchantability: 10
  Max Stack: 1
  Repair: Has repair recipe
\`\`\`

Each line is individually toggleable so you can show exactly what you need.`,
          },
          {
            title: "Food Details",
            content: `For edible items, both the HUD and tooltip show:

\`\`\`
Food: +6 🍖   Saturation: 14.4
  Can always eat
\`\`\`

::tip
Saturation is shown as the **effective value** (nutrition × saturationModifier × 2), matching how it actually replenishes the bar — not the raw modifier stored internally.
::`,
          },
          {
            title: "Tooltip Field Reference",
            content: `| Field | Config Key | Default |
|---|---|---|
| Registry ID | tooltipShowId | ✅ On |
| Source mod | tooltipShowMod | ✅ On |
| Durability % | tooltipShowDurability | ✅ On |
| Burn time | tooltipShowBurnTime | ✅ On |
| Food value | tooltipShowFood | ✅ On |
| Enchantability | tooltipShowEnchantability | ✅ On |
| Max stack | tooltipShowMaxStack | ✅ On |
| Repair item | tooltipShowRepairItem | ✅ On |
| Tags | tooltipShowTags | ❌ Off |
| NBT summary | tooltipShowNbt | ❌ Off |`,
          },
        ],
      },
    ],
  },
  {
    group: "Configuration",
    pages: [
      {
        id: "config",
        icon: "⚙️",
        label: "Config Reference",
        file: "configuration.md",
        badge: null,
        sections: [
          {
            title: "File Location",
            content: `\`\`\`
.minecraft/config/neoi-client.toml
\`\`\`

Generated automatically on first launch. Editable with any text editor. Changes take effect after reloading the world (or immediately if made through the in-game GUI).`,
          },
          {
            title: "hud_general",
            content: `\`\`\`toml
[hud_general]
  # Master on/off switch
  hudEnabled = true

  # Display verbosity — COMPACT | NORMAL | VERBOSE
  displayMode = "NORMAL"

  # HUD position in pixels from screen edges
  hudX = 4
  hudY = 4

  # Text scale percentage (25–200)
  hudScale = 100

  # Background panel behind text
  hudBackground = true
  hudBackgroundAlpha = 120   # 0 = invisible, 255 = opaque

  # Drop shadow on all text
  hudShadow = true
\`\`\``,
          },
          {
            title: "block_info",
            content: `\`\`\`toml
[block_info]
  showBlockName        = true
  showBlockId          = true
  showBlockMod         = true
  showBlockHardness    = true
  showBlockResistance  = true
  showBlockTool        = true
  showBlockLight       = true
  showBlockRedstone    = true
  showBlockFlammable   = true
  showBlockFriction    = true
  showBlockState       = true
  showBlockTags        = true
  showTileEntityData   = true
  showBlockReplaceable = false
  showBlockSolid       = false
  showBlockSpeedFactor = false
  showBlockJumpFactor  = false
  showBlockMapColor    = false
  showBlockDrops       = false
\`\`\``,
          },
          {
            title: "entity_info",
            content: `\`\`\`toml
[entity_info]
  showEntityName         = true
  showEntityId           = true
  showEntityMod          = true
  showEntityType         = true
  showEntityHealth       = true
  showEntityMaxHealth    = true
  showEntityArmor        = true
  showEntitySpeed        = true
  showEntityAttackDamage = true
  showEntityEffects      = true
  showEntityPosition     = true
  showEntityDimension    = true
  showEntityTags         = true
  showEntityNbt          = false
\`\`\``,
          },
          {
            title: "In-Game GUI",
            content: `Open the settings GUI without touching any files:

**Via Mod Menu (recommended)**
1. Install Mod Menu from Modrinth/CurseForge
2. Main Menu → Mods → NEOI → ⚙ Config

**Via the mod directly**
The config screen is registered as a Forge extension point and can be triggered programmatically.

The GUI exposes every toggle as an **ON / OFF button** plus a Display Mode cycle selector. All changes persist to \`neoi-client.toml\` immediately.`,
          },
        ],
      },
      {
        id: "keybinds",
        icon: "⌨️",
        label: "Keybindings",
        file: "keybindings.md",
        badge: null,
        sections: [
          {
            title: "Default Bindings",
            content: `| Key | Action | Feedback |
|---|---|---|
| **H** | Toggle HUD on / off | \`[NEOI] HUD Enabled\` / \`Disabled\` |
| **J** | Cycle display mode | \`[NEOI] Mode: VERBOSE\` |
| **N** | Toggle NBT display | \`[NEOI] NBT display On\` / \`Off\` |

All three binds show a brief action-bar message (above the hotbar) confirming the change. They can be rebound freely under **Options → Controls → Not Enough Object Info**.`,
          },
          {
            title: "Mode Cycle Order",
            content: `\`\`\`
COMPACT → NORMAL → VERBOSE → COMPACT → ...
\`\`\`

Each press of **J** advances one step. The cycle wraps around infinitely.`,
          },
          {
            title: "NBT Mode Detail",
            content: `Pressing **N** enables NBT output **regardless of the current display mode**. This lets you stay in NORMAL for clean everyday use but expose raw NBT on demand without switching to VERBOSE.

::tip
NBT is capped at 256 characters per field in the HUD to avoid overflowing. Switch to VERBOSE for full untruncated output.
::`,
          },
        ],
      },
    ],
  },
  {
    group: "Reference",
    pages: [
      {
        id: "troubleshooting",
        icon: "🔧",
        label: "Troubleshooting",
        file: "troubleshooting.md",
        badge: null,
        sections: [
          {
            title: "HUD Not Appearing",
            content: `**Check these in order:**

1. Press **H** — the HUD may have been toggled off
2. Make sure \`hudEnabled = true\` in \`neoi-client.toml\`
3. Close F3 if it's open — NEOI hides itself behind the debug screen
4. Confirm the mod jar is in the correct \`mods/\` folder and matches Forge 1.20.1
5. Check \`logs/latest.log\` for \`[NEOI] Client setup complete.\``,
          },
          {
            title: "Entity Stats Show 0",
            content: `Not a bug. Not every entity has every attribute registered. Ambient mobs like bats have no \`ATTACK_DAMAGE\` attribute — NEOI can only read what the entity actually has.

If you expect a stat and see 0, check the entity's attribute list in the Minecraft wiki or the mod source.`,
          },
          {
            title: "Performance Impact",
            content: `NEOI reads block/entity data once per render frame for the single targeted object. This is negligible — no area scans, no chunk reads, no network calls.

If you experience lag, try:
- Setting \`hudScale = 75\` (smaller text = fewer draw calls)
- Disabling \`showBlockTags\` and \`showEntityTags\` (tag lookups iterate tag collections)
- Switching to COMPACT mode`,
          },
          {
            title: "Config Not Saving",
            content: `::warning
If \`neoi-client.toml\` resets on every launch, the file may be read-only.
::

\`\`\`bash
# Linux / macOS
chmod 644 ~/.minecraft/config/neoi-client.toml

# Windows — right-click → Properties → uncheck Read-only
\`\`\``,
          },
          {
            title: "Crash on Launch",
            content: `Include the following when reporting a crash:

1. \`logs/latest.log\` — full file, not a screenshot
2. Your Minecraft version (must be exactly 1.20.1)
3. Your Forge version (must be 47.2.0+)
4. List of all other installed mods
5. Any changes made to \`neoi-client.toml\`

Open an issue at the [GitHub repository](https://github.com/EmanuelPlays/NotEnoughObjectInfo/issues).`,
          },
        ],
      },
      {
        id: "faq",
        icon: "❓",
        label: "FAQ",
        file: "faq.md",
        badge: null,
        sections: [
          { title: "Is it client-side only?", content: `Yes — fully client-side. You can join **vanilla servers**, servers without NEOI, and modded servers that don't have NEOI installed. No server-side component exists or is needed.` },
          { title: "Does it work with any Forge mod?", content: `Yes. NEOI reads from Forge and Minecraft registries, so it automatically displays correct IDs and mod sources for blocks, entities, and items from **any** installed Forge mod.` },
          { title: "Can I move the HUD to the right?", content: `Set \`hudX\` to a large value (e.g. \`hudX = 850\` on 1080p). Right-anchoring based on screen width is planned for a future release.` },
          { title: "Why does the HUD disappear with F3?", content: `Intentional. NEOI hides itself when the vanilla debug overlay is open to avoid conflicting displays. It returns the moment you close F3.` },
          { title: "Can I use NEOI in a modpack?", content: `Yes — subject to the **CC BY-ND 4.0** license. You can redistribute it in modpacks and showcase it publicly. You may not modify or repackage the jar. Credit to EmanuelPlays is required.` },
          { title: "Will there be a Fabric port?", content: `Not planned at this time. NEOI is built on Forge-specific APIs. A community port would require explicit permission under the current license terms.` },
        ],
      },
      {
        id: "license",
        icon: "📜",
        label: "License",
        file: "LICENSE.md",
        badge: null,
        sections: [
          {
            title: "CC BY-ND 4.0 License",
            content: `::tip
This mod is protected under the Creative Commons Attribution–NoDerivatives 4.0 International License.
::

**Copyright © 2026 EmanuelPlays**

This work is licensed under the **Creative Commons Attribution–NoDerivatives 4.0 International License** (CC BY-ND 4.0).`,
          },
          {
            title: "You Are Free To",
            content: `- **Use** this mod in your Minecraft instance
- **Share** and redistribute this mod in any format
- **Include** it in modpacks or showcase it publicly
- **Credit** the author (EmanuelPlays) when sharing

::tip
Modpack inclusion is permitted as long as the original jar is used unmodified and EmanuelPlays is credited.
::`,
          },
          {
            title: "You May NOT",
            content: `::danger
The following actions are strictly prohibited without explicit written permission from the author.
::

- **Modify, edit, translate, or create derivative works** based on this mod
- **Repackage or rebrand** the mod under a different name or identity
- **Remove or alter** authorship or licensing information from the jar or source
- **Port** this mod to other mod loaders (Fabric, Quilt, etc.) without permission`,
          },
          {
            title: "Requesting Permission",
            content: `To request permission for modifications, translations, ports, or any derivative use, contact the author directly via the GitHub repository.

Please include in your request:
- What you intend to create or modify
- How it will be distributed
- Whether it will be monetised in any way

**License details:** [creativecommons.org/licenses/by-nd/4.0](https://creativecommons.org/licenses/by-nd/4.0)`,
          },
        ],
      },
    ],
  },
];

// Flatten all pages
const ALL_PAGES = DOCS.flatMap(g => g.pages);

// ─── MARKDOWN RENDERER ────────────────────────────────────────────────────────
function inlineRender(text) {
  const parts = [];
  const regex = /(\*\*(.+?)\*\*|`([^`]+)`|\[([^\]]+)\]\(([^)]+)\))/g;
  let last = 0, m;
  while ((m = regex.exec(text)) !== null) {
    if (m.index > last) parts.push(text.slice(last, m.index));
    if (m[0].startsWith("**"))
      parts.push(<strong key={m.index} style={{ color: T.text, fontWeight: 700 }}>{m[2]}</strong>);
    else if (m[0].startsWith("`"))
      parts.push(<code key={m.index} style={{ background: T.bgCode, color: T.textCode, padding: "1px 6px", borderRadius: 4, fontFamily: "'JetBrains Mono', monospace", fontSize: "0.85em", border: `1px solid ${T.border}` }}>{m[3]}</code>);
    else if (m[0].startsWith("["))
      parts.push(<a key={m.index} href={m[5]} target="_blank" rel="noreferrer" style={{ color: T.accent, textDecoration: "none", borderBottom: `1px solid ${T.accentGlow}` }}>{m[4]}</a>);
    last = m.index + m[0].length;
  }
  if (last < text.length) parts.push(text.slice(last));
  return parts.length ? parts : text;
}

function CopyButton({ code }) {
  const [copied, setCopied] = useState(false);
  return (
    <button onClick={() => { navigator.clipboard?.writeText(code); setCopied(true); setTimeout(() => setCopied(false), 1800); }}
      style={{ position: "absolute", top: 10, right: 10, background: copied ? T.success : T.bgHover, border: `1px solid ${T.border}`, color: copied ? "#fff" : T.textMuted, borderRadius: 5, padding: "3px 10px", fontSize: 11, cursor: "pointer", fontFamily: "'JetBrains Mono', monospace", transition: "all .2s" }}>
      {copied ? "✓ Copied" : "Copy"}
    </button>
  );
}

function renderMd(text) {
  const lines = text.split("\n");
  const out = []; let i = 0;
  while (i < lines.length) {
    const line = lines[i];

    // callout ::type ... ::
    if (line.startsWith("::")) {
      const type = line.slice(2).trim();
      const calloutColors = { tip: { bg: "#0e1a2e", border: T.accent, icon: "💡", text: "#93c5fd" }, warning: { bg: "#1a150a", border: T.warning, icon: "⚠️", text: "#fcd34d" }, danger: { bg: "#1a0a0a", border: T.danger, icon: "🚫", text: "#fca5a5" } };
      const c = calloutColors[type] || calloutColors.tip;
      const content = []; i++;
      while (i < lines.length && !lines[i].startsWith("::")) { content.push(lines[i]); i++; }
      out.push(<div key={i} style={{ background: c.bg, border: `1px solid ${c.border}`, borderLeft: `3px solid ${c.border}`, borderRadius: 6, padding: "12px 16px", margin: "16px 0", color: c.text, fontSize: 14 }}>
        <span style={{ marginRight: 6 }}>{c.icon}</span>{content.map(l => inlineRender(l)).reduce((a, b) => <>{a} {b}</>)}
      </div>); i++; continue;
    }

    // fenced code block
    if (line.startsWith("```")) {
      const code = []; i++;
      while (i < lines.length && !lines[i].startsWith("```")) { code.push(lines[i]); i++; }
      const codeStr = code.join("\n");
      out.push(<div key={i} style={{ position: "relative", margin: "16px 0" }}>
        <pre style={{ background: T.bgCode, border: `1px solid ${T.border}`, borderRadius: 8, padding: "16px 18px", overflowX: "auto", fontFamily: "'JetBrains Mono', monospace", fontSize: 13, color: T.textCode, lineHeight: 1.65, margin: 0 }}>{codeStr}</pre>
        <CopyButton code={codeStr} />
      </div>); i++; continue;
    }

    // table
    if (line.startsWith("|")) {
      const rows = []; while (i < lines.length && lines[i].startsWith("|")) { if (!/^\|[-| :]+\|$/.test(lines[i])) rows.push(lines[i]); i++; }
      out.push(<div key={i} style={{ overflowX: "auto", margin: "16px 0", borderRadius: 8, border: `1px solid ${T.border}` }}>
        <table style={{ borderCollapse: "collapse", width: "100%", fontSize: 13.5 }}>
          <tbody>{rows.map((row, ri) => {
            const cells = row.split("|").filter((_, ci) => ci > 0 && ci < row.split("|").length - 1);
            const isHead = ri === 0;
            return <tr key={ri} style={{ background: isHead ? T.bgHover : ri % 2 === 0 ? T.bgCard : "transparent", borderBottom: `1px solid ${T.border}` }}>
              {cells.map((cell, ci) => {
                const Tag = isHead ? "th" : "td";
                return <Tag key={ci} style={{ padding: "10px 16px", textAlign: "left", color: isHead ? T.accentHover : T.text, fontWeight: isHead ? 600 : 400, fontFamily: isHead ? "'Outfit', sans-serif" : "inherit", whiteSpace: "nowrap" }}>{inlineRender(cell.trim())}</Tag>;
              })}
            </tr>;
          })}</tbody>
        </table>
      </div>); continue;
    }

    // list
    if (/^(\d+\.|[-*]) /.test(line)) {
      const items = []; const ordered = /^\d+\./.test(line);
      while (i < lines.length && /^(\d+\.|[-*]) /.test(lines[i])) { items.push(lines[i].replace(/^(\d+\.|[-*]) /, "")); i++; }
      const Tag = ordered ? "ol" : "ul";
      out.push(<Tag key={i} style={{ paddingLeft: 22, margin: "8px 0 12px", color: T.text, lineHeight: 1.9 }}>{items.map((item, idx) => <li key={idx} style={{ marginBottom: 4 }}>{inlineRender(item)}</li>)}</Tag>); continue;
    }

    if (line.trim() === "") { out.push(<div key={i} style={{ height: 10 }} />); i++; continue; }

    out.push(<p key={i} style={{ margin: "4px 0 10px", lineHeight: 1.8, color: T.text, fontSize: 15 }}>{inlineRender(line)}</p>);
    i++;
  }
  return out;
}

// ─── COMPONENTS ───────────────────────────────────────────────────────────────

function Badge({ type }) {
  const styles = { new: { bg: "#1e3a5f", color: "#60a5fa", label: "NEW" }, beta: { bg: "#2a1a4a", color: "#a78bfa", label: "BETA" } };
  const s = styles[type]; if (!s) return null;
  return <span style={{ background: s.bg, color: s.color, fontSize: 9, fontWeight: 700, padding: "1px 6px", borderRadius: 3, letterSpacing: 1, marginLeft: 6, fontFamily: "monospace" }}>{s.label}</span>;
}

function SearchModal({ onClose, setActive }) {
  const [q, setQ] = useState("");
  const inputRef = useRef();
  useEffect(() => { inputRef.current?.focus(); }, []);
  useEffect(() => {
    const handler = (e) => { if (e.key === "Escape") onClose(); };
    window.addEventListener("keydown", handler);
    return () => window.removeEventListener("keydown", handler);
  }, [onClose]);

  const results = q.length < 2 ? [] : ALL_PAGES.flatMap(page =>
    page.sections
      .filter(s => s.title.toLowerCase().includes(q.toLowerCase()) || s.content.toLowerCase().includes(q.toLowerCase()) || page.label.toLowerCase().includes(q.toLowerCase()))
      .map(s => ({ page, section: s }))
  ).slice(0, 8);

  return (
    <div onClick={onClose} style={{ position: "fixed", inset: 0, background: "rgba(0,0,0,0.7)", zIndex: 1000, display: "flex", alignItems: "flex-start", justifyContent: "center", paddingTop: 80 }}>
      <div onClick={e => e.stopPropagation()} style={{ background: T.bgSidebar, border: `1px solid ${T.borderLight}`, borderRadius: 12, width: 580, boxShadow: `0 24px 60px rgba(0,0,0,0.5)`, overflow: "hidden" }}>
        <div style={{ display: "flex", alignItems: "center", padding: "14px 16px", borderBottom: `1px solid ${T.border}`, gap: 10 }}>
          <span style={{ color: T.textMuted, fontSize: 16 }}>🔍</span>
          <input ref={inputRef} value={q} onChange={e => setQ(e.target.value)} placeholder="Search documentation…" style={{ flex: 1, background: "none", border: "none", outline: "none", color: T.text, fontSize: 15, fontFamily: "'Outfit', sans-serif" }} />
          <kbd style={{ background: T.bgCard, border: `1px solid ${T.border}`, borderRadius: 4, padding: "2px 6px", fontSize: 11, color: T.textMuted }}>ESC</kbd>
        </div>
        <div style={{ maxHeight: 400, overflowY: "auto" }}>
          {results.length === 0 && q.length >= 2 && <div style={{ padding: "24px", color: T.textMuted, textAlign: "center", fontSize: 14 }}>No results for "{q}"</div>}
          {results.length === 0 && q.length < 2 && <div style={{ padding: "16px 18px", color: T.textDim, fontSize: 13 }}>Start typing to search across all pages…</div>}
          {results.map(({ page, section }, i) => (
            <div key={i} onClick={() => { setActive(page.id); onClose(); }} style={{ padding: "12px 18px", cursor: "pointer", borderBottom: `1px solid ${T.border}`, transition: "background .15s" }}
              onMouseEnter={e => e.currentTarget.style.background = T.bgHover} onMouseLeave={e => e.currentTarget.style.background = "transparent"}>
              <div style={{ fontSize: 12, color: T.textMuted, marginBottom: 2, fontFamily: "monospace" }}>{page.icon} {page.label} › {page.file}</div>
              <div style={{ color: T.text, fontSize: 14, fontWeight: 600 }}>{section.title}</div>
              <div style={{ color: T.textMuted, fontSize: 12, marginTop: 2, overflow: "hidden", whiteSpace: "nowrap", textOverflow: "ellipsis" }}>
                {section.content.slice(0, 100).replace(/[#*`\n]/g, " ")}…
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

function Sidebar({ active, setActive, collapsed, setCollapsed }) {
  const [searchOpen, setSearchOpen] = useState(false);
  useEffect(() => {
    const handler = (e) => { if ((e.metaKey || e.ctrlKey) && e.key === "k") { e.preventDefault(); setSearchOpen(true); } };
    window.addEventListener("keydown", handler);
    return () => window.removeEventListener("keydown", handler);
  }, []);

  return (
    <>
      {searchOpen && <SearchModal onClose={() => setSearchOpen(false)} setActive={(id) => { setActive(id); setSearchOpen(false); }} />}
      <aside style={{ width: collapsed ? 56 : 260, flexShrink: 0, background: T.bgSidebar, borderRight: `1px solid ${T.border}`, display: "flex", flexDirection: "column", height: "100vh", position: "sticky", top: 0, transition: "width .2s", overflow: "hidden" }}>
        {/* Header */}
        <div style={{ padding: collapsed ? "16px 12px" : "18px 16px", borderBottom: `1px solid ${T.border}`, display: "flex", alignItems: "center", justifyContent: "space-between", minHeight: 60 }}>
          {!collapsed && (
            <div>
              <div style={{ fontFamily: "'Outfit', sans-serif", fontWeight: 800, fontSize: 15, color: T.text, letterSpacing: -0.3 }}>⚡ NEOI Docs</div>
              <div style={{ fontSize: 11, color: T.textMuted, marginTop: 1, fontFamily: "monospace" }}>v1.0.0 · Forge 1.20.1</div>
            </div>
          )}
          <button onClick={() => setCollapsed(c => !c)} style={{ background: "none", border: "none", color: T.textMuted, cursor: "pointer", fontSize: 16, padding: 4, borderRadius: 4, lineHeight: 1 }}>{collapsed ? "→" : "←"}</button>
        </div>

        {/* Search */}
        {!collapsed && (
          <div style={{ padding: "10px 12px" }}>
            <button onClick={() => setSearchOpen(true)} style={{ display: "flex", alignItems: "center", gap: 8, width: "100%", background: T.bgCard, border: `1px solid ${T.border}`, borderRadius: 7, padding: "7px 12px", color: T.textMuted, fontSize: 13, cursor: "pointer", fontFamily: "'Outfit', sans-serif" }}>
              <span>🔍</span><span style={{ flex: 1, textAlign: "left" }}>Search…</span>
              <kbd style={{ background: T.bgSidebar, border: `1px solid ${T.border}`, borderRadius: 3, padding: "1px 5px", fontSize: 10 }}>⌘K</kbd>
            </button>
          </div>
        )}

        {/* Nav */}
        <nav style={{ flex: 1, overflowY: "auto", padding: collapsed ? "8px 6px" : "8px 10px" }}>
          {DOCS.map(group => (
            <div key={group.group} style={{ marginBottom: 16 }}>
              {!collapsed && <div style={{ fontSize: 10, fontWeight: 700, color: T.textDim, letterSpacing: 1.5, textTransform: "uppercase", padding: "4px 8px 6px", fontFamily: "'Outfit', sans-serif" }}>{group.group}</div>}
              {group.pages.map(page => {
                const isActive = active === page.id;
                return (
                  <button key={page.id} onClick={() => setActive(page.id)} title={collapsed ? page.label : undefined}
                    style={{ display: "flex", alignItems: "center", gap: 8, width: "100%", textAlign: "left", background: isActive ? T.accentDim : "transparent", border: "none", borderRadius: 6, padding: collapsed ? "8px" : "7px 10px", color: isActive ? T.accentHover : T.textMuted, cursor: "pointer", fontSize: 13.5, fontFamily: "'Outfit', sans-serif", fontWeight: isActive ? 600 : 400, transition: "all .12s", marginBottom: 1, justifyContent: collapsed ? "center" : "flex-start", position: "relative" }}
                    onMouseEnter={e => { if (!isActive) e.currentTarget.style.background = T.bgHover; e.currentTarget.style.color = T.text; }}
                    onMouseLeave={e => { if (!isActive) { e.currentTarget.style.background = "transparent"; e.currentTarget.style.color = T.textMuted; } }}>
                    {isActive && !collapsed && <div style={{ position: "absolute", left: 0, top: "50%", transform: "translateY(-50%)", width: 3, height: "60%", background: T.accent, borderRadius: "0 2px 2px 0" }} />}
                    <span style={{ fontSize: 15, flexShrink: 0 }}>{page.icon}</span>
                    {!collapsed && <><span style={{ flex: 1 }}>{page.label}</span>{page.badge && <Badge type={page.badge} />}</>}
                  </button>
                );
              })}
            </div>
          ))}
        </nav>

        {/* Footer */}
        {!collapsed && (
          <div style={{ padding: "12px 16px", borderTop: `1px solid ${T.border}`, fontSize: 11, color: T.textDim, fontFamily: "monospace" }}>
            © 2026 EmanuelPlays · CC BY-ND 4.0
          </div>
        )}
      </aside>
    </>
  );
}

function TableOfContents({ page }) {
  const [active, setActive] = useState(0);
  return (
    <div style={{ width: 200, flexShrink: 0, padding: "32px 0 0 0" }}>
      <div style={{ position: "sticky", top: 24 }}>
        <div style={{ fontSize: 10, fontWeight: 700, color: T.textDim, letterSpacing: 1.5, textTransform: "uppercase", marginBottom: 10, fontFamily: "'Outfit', sans-serif" }}>On this page</div>
        {page.sections.map((s, i) => (
          <div key={i} onClick={() => setActive(i)} style={{ fontSize: 12.5, color: active === i ? T.accent : T.textMuted, padding: "5px 0 5px 12px", cursor: "pointer", borderLeft: `2px solid ${active === i ? T.accent : T.border}`, marginBottom: 2, transition: "all .12s", fontFamily: "'Outfit', sans-serif" }}
            onMouseEnter={e => e.currentTarget.style.color = T.text} onMouseLeave={e => e.currentTarget.style.color = active === i ? T.accent : T.textMuted}>
            {s.title}
          </div>
        ))}
      </div>
    </div>
  );
}

function PrevNext({ page, setActive }) {
  const idx = ALL_PAGES.findIndex(p => p.id === page.id);
  const prev = ALL_PAGES[idx - 1];
  const next = ALL_PAGES[idx + 1];
  return (
    <div style={{ display: "flex", gap: 16, marginTop: 48, paddingTop: 24, borderTop: `1px solid ${T.border}` }}>
      {prev ? (
        <button onClick={() => setActive(prev.id)} style={{ flex: 1, background: T.bgCard, border: `1px solid ${T.border}`, borderRadius: 8, padding: "14px 18px", cursor: "pointer", textAlign: "left", color: T.text, fontFamily: "'Outfit', sans-serif", transition: "border-color .15s" }}
          onMouseEnter={e => e.currentTarget.style.borderColor = T.accent} onMouseLeave={e => e.currentTarget.style.borderColor = T.border}>
          <div style={{ fontSize: 11, color: T.textMuted, marginBottom: 4 }}>← Previous</div>
          <div style={{ fontWeight: 600, fontSize: 14 }}>{prev.icon} {prev.label}</div>
        </button>
      ) : <div style={{ flex: 1 }} />}
      {next ? (
        <button onClick={() => setActive(next.id)} style={{ flex: 1, background: T.bgCard, border: `1px solid ${T.border}`, borderRadius: 8, padding: "14px 18px", cursor: "pointer", textAlign: "right", color: T.text, fontFamily: "'Outfit', sans-serif", transition: "border-color .15s" }}
          onMouseEnter={e => e.currentTarget.style.borderColor = T.accent} onMouseLeave={e => e.currentTarget.style.borderColor = T.border}>
          <div style={{ fontSize: 11, color: T.textMuted, marginBottom: 4 }}>Next →</div>
          <div style={{ fontWeight: 600, fontSize: 14 }}>{next.icon} {next.label}</div>
        </button>
      ) : <div style={{ flex: 1 }} />}
    </div>
  );
}

function ContentArea({ page, setActive }) {
  const mainRef = useRef();
  useEffect(() => { if (mainRef.current) mainRef.current.scrollTop = 0; }, [page.id]);
  const groupLabel = DOCS.find(g => g.pages.find(p => p.id === page.id))?.group;

  return (
    <main ref={mainRef} style={{ flex: 1, overflowY: "auto", minWidth: 0 }}>
      {/* Top bar */}
      <div style={{ position: "sticky", top: 0, zIndex: 10, background: `${T.bg}ee`, borderBottom: `1px solid ${T.border}`, backdropFilter: "blur(8px)", padding: "10px 40px", display: "flex", alignItems: "center", gap: 6, fontSize: 12, color: T.textMuted, fontFamily: "monospace" }}>
        <span>NEOI</span><span style={{ color: T.textDim }}>/</span>
        <span style={{ color: T.textDim }}>{groupLabel}</span><span style={{ color: T.textDim }}>/</span>
        <span style={{ color: T.accent }}>{page.file}</span>
        <div style={{ marginLeft: "auto", display: "flex", gap: 8, alignItems: "center" }}>
          {page.badge && <Badge type={page.badge} />}
          <span style={{ color: T.textDim }}>Last updated: March 2026</span>
        </div>
      </div>

      <div style={{ display: "flex", maxWidth: 1100, margin: "0 auto" }}>
        {/* Main content */}
        <div style={{ flex: 1, padding: "40px 48px 80px", minWidth: 0 }}>
          {/* Page title */}
          <div style={{ marginBottom: 36 }}>
            <div style={{ fontSize: 32, marginBottom: 8 }}>{page.icon}</div>
            <h1 style={{ fontFamily: "'Outfit', sans-serif", fontSize: 32, fontWeight: 800, color: T.textHeading, margin: "0 0 12px", letterSpacing: -0.8, lineHeight: 1.2 }}>{page.label}</h1>
            <div style={{ display: "flex", alignItems: "center", gap: 10 }}>
              <span style={{ fontFamily: "monospace", fontSize: 12, color: T.textMuted, background: T.bgCard, border: `1px solid ${T.border}`, padding: "3px 10px", borderRadius: 4 }}>📄 {page.file}</span>
            </div>
          </div>

          {/* Sections */}
          {page.sections.map((s, i) => (
            <div key={i} style={{ marginBottom: 44 }}>
              <h2 style={{ fontFamily: "'Outfit', sans-serif", fontSize: 20, fontWeight: 700, color: T.textHeading, margin: "0 0 16px", paddingBottom: 10, borderBottom: `1px solid ${T.border}`, display: "flex", alignItems: "center", gap: 8 }}>
                <span style={{ color: T.accent, fontSize: 14 }}>§</span>{s.title}
              </h2>
              <div style={{ fontSize: 15, lineHeight: 1.8 }}>{renderMd(s.content)}</div>
            </div>
          ))}

          <PrevNext page={page} setActive={setActive} />

          {/* Edit link */}
          <div style={{ marginTop: 24, textAlign: "center", fontSize: 12, color: T.textDim }}>
            <a href={`https://github.com/EmanuelPlays/NotEnoughObjectInfo/edit/main/docs/${page.file}`} target="_blank" rel="noreferrer"
              style={{ color: T.textMuted, textDecoration: "none" }} onMouseEnter={e => e.target.style.color = T.accent} onMouseLeave={e => e.target.style.color = T.textMuted}>
              ✎ Edit this page on GitHub
            </a>
          </div>
        </div>

        {/* Right TOC */}
        <div style={{ display: "flex", paddingRight: 32 }}>
          <TableOfContents page={page} />
        </div>
      </div>
    </main>
  );
}

// ─── APP ──────────────────────────────────────────────────────────────────────
export default function App() {
  const [active, setActive] = useState(ALL_PAGES[0].id);
  const [collapsed, setCollapsed] = useState(false);
  const page = ALL_PAGES.find(p => p.id === active) || ALL_PAGES[0];

  return (
    <>
      <style>{`
        @import url('https://fonts.googleapis.com/css2?family=Outfit:wght@400;600;700;800&family=JetBrains+Mono:wght@400;500&display=swap');
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
        html, body { height: 100%; background: ${T.bg}; color: ${T.text}; font-family: 'Outfit', system-ui, sans-serif; }
        ::-webkit-scrollbar { width: 5px; height: 5px; }
        ::-webkit-scrollbar-track { background: transparent; }
        ::-webkit-scrollbar-thumb { background: ${T.border}; border-radius: 4px; }
        ::-webkit-scrollbar-thumb:hover { background: ${T.borderLight}; }
        a { color: ${T.accent}; }
        button { font-family: 'Outfit', sans-serif; }
      `}</style>
      <div style={{ display: "flex", height: "100vh", overflow: "hidden" }}>
        <Sidebar active={active} setActive={setActive} collapsed={collapsed} setCollapsed={setCollapsed} />
        <ContentArea page={page} setActive={setActive} />
      </div>
    </>
  );
}
