package com.nashss.se.connexionservice.models;

import java.util.List;
import java.util.Objects;

public class HobbyModel {
    private List<String> hobbiesList;

    /**
     * Non-Empty constructor for Category POJO.
     * @param hobbiesList categories parameter
     */
    public HobbyModel(List<String> hobbiesList) {
        this.hobbiesList = hobbiesList;
    }

    public List<String> getHobbies() {
        return hobbiesList;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbiesList = hobbies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HobbyModel that = (HobbyModel) o;
        return Objects.equals(hobbiesList, that.hobbiesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hobbiesList);
    }

    @Override
    public String toString() {
        return "Hobby{" +
                "hobby='" + hobbiesList + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    public static class Builder {
        private List<String> hobbiesList;

        public Builder withHobbies(List<String> hobbiesList) {
            this.hobbiesList = hobbiesList;
            return this;
        }

        public HobbyModel build() {

            return new HobbyModel(hobbiesList);
        }
    }
}
