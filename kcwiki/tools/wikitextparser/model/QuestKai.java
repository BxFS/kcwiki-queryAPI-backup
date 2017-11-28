package org.kcwiki.tools.wikitextparser.model;

import java.util.ArrayList;
import java.util.List;



/**
 * @author noobFS
 * 
 */
public class QuestKai {
    private String type;
    private int category;
    private String wiki_id;
    private String game_id;
    private List<String> prereqs;
    private List<String> unlocks;
    private MultiLanguageEntry titles;
    private MultiLanguageEntry descriptions;
    private MultiLanguageEntry note;
    private RewardEntity rewards;
    
    public QuestKai() {
        this.type = "";
        this.category = -1;
        this.wiki_id = "";
        this.game_id = "";
        this.prereqs = new ArrayList<String>();
        this.unlocks = new ArrayList<String>();
        this.titles = new MultiLanguageEntry();
        this.descriptions = new MultiLanguageEntry();
        this.note = new MultiLanguageEntry();
        this.rewards = new RewardEntity();
    }
    
    public static class RewardEntity {
        private int fuel;
        private int ammo;
        private int steel;
        private int bauxite;
        private int bucket;
        private int devmat;
        private int flamethrower; //Flamuthrower
        private int screw;
        private int ship;
        private List<EquipmentEntity> equipments;
        private List<ItemEntity> items;
        
        public RewardEntity() {
            this.fuel = 0;
            this.ammo = 0;
            this.steel = 0;
            this.bauxite = 0;
            this.bucket = 0;
            this.devmat = 0;
            this.flamethrower = 0;
            this.screw = 0;
            this.ship = 0;
            this.equipments = new ArrayList<EquipmentEntity>();
            this.items = new ArrayList<ItemEntity>();
            
        }
        
        public int getFuel() {
          return fuel;
        }

        public void setFuel(int fuel) {
          this.fuel = fuel;
        }

        public int getAmmo() {
          return ammo;
        }

        public void setAmmo(int ammo) {
          this.ammo = ammo;
        }

        public int getSteel() {
          return steel;
        }

        public void setSteel(int steel) {
          this.steel = steel;
        }

        public int getBauxite() {
          return bauxite;
        }

        public void setBauxite(int bauxite) {
          this.bauxite = bauxite;
        }

        public int getBucket() {
          return bucket;
        }

        public void setBucket(int bucket) {
          this.bucket = bucket;
        }

        public int getDevmat() {
          return devmat;
        }

        public void setDevmat(int devmat) {
          this.devmat = devmat;
        }

        public int getFlamethrower() {
          return flamethrower;
        }

        public void setFlamethrower(int flamethrower) {
          this.flamethrower = flamethrower;
        }

        public int getScrew() {
          return screw;
        }

        public void setScrew(int screw) {
          this.screw = screw;
        }

        public int getShip() {
          return ship;
        }

        public void setShips(int ship) {
          this.ship = ship;
        }

        public List<EquipmentEntity> getEquipments() {
          return equipments;
        }

        public void setEquipments(List<EquipmentEntity> equipments) {
          this.equipments = equipments;
        }

        public List<ItemEntity> getItems() {
          return items;
        }

        public void setItems(List<ItemEntity> items) {
          this.items = items;
        }
        
        public static class EquipmentEntity {
            private int id;
            private int amount;
            
            public EquipmentEntity(int id, int amount) {
                this.id = id;
                this.amount = amount;
            }
            
            public void setId(int id) {
              this.id = id;
            }

            public void setAmount(int amount) {
              this.amount = amount;
            }

            public int getId() {
                return this.id;
            }
            
            public int getAmount() {
                return this.amount;
            }
        }
        
        public static class ItemEntity extends EquipmentEntity{
            private boolean purchasable;
            
            public ItemEntity(int id, int amount, boolean purchasable) {
                super(id, amount);
                this.purchasable = purchasable;
            }
            
            public void setPurchasable(boolean purchasable) {
              this.purchasable = purchasable;
            }

            public boolean getPurchasable() {
                return this.purchasable;
            }
        }
        
    }
    

}
