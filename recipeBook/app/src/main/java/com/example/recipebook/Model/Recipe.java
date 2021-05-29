package com.example.recipebook.Model;

public class Recipe {
     String name, desc,chef,imageUrl;

     public Recipe(String name, String desc, String chef, String imageUrl) {
          this.name = name;
          this.desc = desc;
          this.chef = chef;
          this.imageUrl = imageUrl;
     }

     public Recipe() {
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getDesc() {
          return desc;
     }

     public void setDesc(String desc) {
          this.desc = desc;
     }

     public String getChef() {
          return chef;
     }

     public void setChef(String chef) {
          this.chef = chef;
     }

     public String getImageUrl() {
          return imageUrl;
     }

     public void setImageUrl(String imageUrl) {
          this.imageUrl = imageUrl;
     }
}
