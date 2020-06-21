#Hipogriff Kingdoms Lib
Plugin base para la creación de otros plugins.

Incluye:
- [x] Creación de mapas de idioma.
- [x] Creación de menus de interfaz.
- [ ] Creación de menus de hotbar.

##Creación de menus de interfaz
Menú de ejemplo (menu/menu1.yml):  
`menu1` es el nombre del archivo, al igual que el **puntero** del menu en el mapa de idioma.
```yaml
#----------------#
# 1 <= ROWS <= 6 #
#----------------#
rows: 6
title: "location.menu.menu1.title"
description: "location.menu.menu1.description"
open-actions:
    sound: "block.note_block.pling"
close-actions:
    sound: "entity.bat.hurt"
icons:
    lobby:
        item: "grass_block"
        title: "location.menu.menu1.icons.lobby.title"
        description: "location.menu.menu1.icons.lobby.lore"
        x: 5
        y: 5
        click-actions:
            server: "lobby"
```
- rows: entero >= que 0 y <= que 6
- title: **puntero** con referencia a `lang/code-lang.yml`
- description: **puntero** con referencia a `lang/code-lang.yml`
- open-actions: Acciones de menú al abrir.
    - feedback: Contesta al jugador.
    - message: Manda un mensaje a alguien.
    - broadcast: Manda un mensaje a todos los jugadores del servidor.
    - command: Ejecuta un comando como el jugador.
    - sound: Reproduce un sonido.
    - menu: Abre un menu.
    - server: Cambia al jugador de servidor con [BungeeCord](https://github.com/SpigotMC/BungeeCord).
- close-actions: Acciones de menú al cerrar.
    - open-actions: Acciones de menú al abrir.
    - feedback: Contesta al jugador.
    - message: Manda un mensaje a alguien.
    - broadcast: Manda un mensaje a todos los jugadores del servidor.
    - command: Ejecuta un comando como el jugador.
    - sound: Reproduce un sonido.
    - menu: Abre un menu.
    - server: Cambia al jugador de servidor con [BungeeCord](https://github.com/SpigotMC/BungeeCord)
- icons: Lista de iconos en el menú.
    - Nombre del icono.
        - item: Minecraft id sin `minecraft:`.
        - title: **puntero** con referencia a `lang/code-lang.yml`
        - description: **puntero** con referencia a `lang/code-lang.yml`
        - x: entero >= que 1 y <= que 9
        - y: entero >= que 1 y <= que `rows`
