package Main;

import entity.Entity;

public class CollisonChecker {
    GamePanel gp;

    public CollisonChecker(GamePanel gp) {

        this.gp = gp;
    }

    public void checkerTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;
        int tileNum1, tileNum2;
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[gp.currnetMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[gp.currnetMap][entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true
                        || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[gp.currnetMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNumber[gp.currnetMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true
                        || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[gp.currnetMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[gp.currnetMap][entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true
                        || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[gp.currnetMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[gp.currnetMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true
                        || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currnetMap][i] != null) {
                //get entity solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //get object solid area position
                gp.obj[gp.currnetMap][i].solidArea.x = gp.obj[gp.currnetMap][i].worldX + gp.obj[gp.currnetMap][i].solidArea.x;
                gp.obj[gp.currnetMap][i].solidArea.y = gp.obj[gp.currnetMap][i].worldY + gp.obj[gp.currnetMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        //checking if two rectangle coliding
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }
                if (entity.solidArea.intersects(gp.obj[gp.currnetMap][i].solidArea)) {
                    if (gp.obj[gp.currnetMap][i].collision == true) {
                        entity.collisionOn = true;
                    }
                    if (player == true) {
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currnetMap][i].solidArea.x = gp.obj[gp.currnetMap][i].solidAreaDefaultX;
                gp.obj[gp.currnetMap][i].solidArea.y = gp.obj[gp.currnetMap][i].solidAreaDefaultY;
            }

        }
        return index;
    }

    //NPC OR MONSTER
    public int checkEntity(Entity entity, Entity[][] target) {

        int index = 999;
        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currnetMap][i] != null) {
                //get entity solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //get object solid area position
                target[gp.currnetMap][i].solidArea.x = target[gp.currnetMap][i].worldX + target[gp.currnetMap][i].solidArea.x;
                target[gp.currnetMap][i].solidArea.y = target[gp.currnetMap][i].worldY + target[gp.currnetMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;

                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;

                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;

                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;

                        break;
                }
                if (entity.solidArea.intersects(target[gp.currnetMap][i].solidArea)) {
                    if (target[gp.currnetMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }

                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currnetMap][i].solidArea.x = target[gp.currnetMap][i].solidAreaDefaultX;
                target[gp.currnetMap][i].solidArea.y = target[gp.currnetMap][i].solidAreaDefaultY;
            }

        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;
        //get entity solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        //get object solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
        }
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return contactPlayer;

    }
}
