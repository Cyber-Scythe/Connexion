package com.nashss.se.connexionservice.models;

import java.util.List;
import java.util.Objects;

import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToList;

public class UserModel {
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final String email;
    private final String id;
    private final int birthMonth;
    private final int birthDay;
    private final int birthYear;
    private final String city;
    private final String state;
    private final String country;
    private final String personalityType;
    private final String aboutMe;
    private final List<String> hobbies;
    private final List<String> connexions;

    /**
     * Constructor for UserModel object.
     * <p>
     * @param firstName The first name of the user
     * @param lastName The last name of the user
     * @param gender The user's gender
     * @param email The email of the user
     * @param id The id of the user
     * @param birthMonth The user's birth month
     * @param birthDay The user's birth day
     * @param birthYear The user's birth year
     * @param city The city the user lives in
     * @param state The state the user lives in
     * @param country The country the user lives in
     * @param personalityType The personality type of the user
     * @param aboutMe The user's About Me section
     * @param hobbies A list of user hobbies
     * @param connexions A list of the user's connexions
     */
    public UserModel(String id,
                      String email,
                      String firstName,
                      String lastName,
                      String gender,
                      int birthMonth,
                      int birthDay,
                      int birthYear,
                      String city,
                      String state,
                      String country,
                      String personalityType,
                      List<String> hobbies,
                      String aboutMe,
                      List<String> connexions) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.id = id;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.birthYear = birthYear;
        this.city = city;
        this.state = state;
        this.country = country;
        this.personalityType = personalityType;
        this.aboutMe = aboutMe;
        this.hobbies = hobbies;
        this.connexions = connexions;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public String getGender() { return gender; }

    public String getEmail() {

        return email;
    }

    public String getId() {
        return id;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public int getBirthDay() { return birthDay; }

    public int getBirthYear() { return birthYear; }

    public String getCity() { return city; }

    public String getState() { return state; }

    public String getCountry() { return country; }

    public String getPersonalityType() {
        return personalityType;
    }

    public String getAboutMe() { return aboutMe; }

    public List<String> getHobbies() {

        return copyToList(hobbies);
    }
    public List<String> getConnexions() {
        return copyToList(connexions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserModel that = (UserModel) o;

        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(birthMonth, that.birthMonth) &&
                Objects.equals(birthDay, that.birthDay) &&
                Objects.equals(birthYear, that.birthYear) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) &&
                Objects.equals(country, that.country) &&
                Objects.equals(personalityType, that.personalityType) &&
                Objects.equals(hobbies, that.hobbies) &&
                Objects.equals(aboutMe, that.aboutMe) &&
                Objects.equals(connexions, that.connexions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, gender, birthMonth, birthDay, birthYear, city, state, country, personalityType, hobbies, aboutMe, connexions);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    public String getUserFirstName() { return firstName; }

    public String getUserLastName() {
        return lastName;
    }

    public String getUserGender() { return gender; }

    public String getUserEmail() {
        return email;
    }
    public int getUserBirthMonth() {
        return birthMonth;
    }
    public int getUserBirthDay() {
        return birthDay;
    }
    public int getUserBirthYear() {
        return birthYear;
    }
    public String getUserId() {
        return id;
    }
    public String getUserCity() {
        return city;
    }
    public String getUserState() {
        return state;
    }
    public String getUserCountry() {
        return country;
    }
    public String getUserPersonalityType() {
        return personalityType;
    }
    public String getUserAboutMe() { return aboutMe; }
    public List<String> getUserHobbies() {
        return hobbies;
    }
    public List<String> getUserConnexions() {
        return connexions;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String gender;
        private String email;
        private String id;
        private int birthMonth;
        private int birthDay;
        private int birthYear;
        private String city;
        private String state;
        private String country;
        private String personalityType;
        private String aboutMe;
        private List<String> hobbies;
        private List<String> connexions;

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withBirthMonth(int birthMonth) {
            this.birthMonth = birthMonth;
            return this;
        }

        public Builder withBirthDay(int birthDay) {
            this.birthDay = birthDay;
            return this;
        }

        public Builder withBirthYear(int birthYear) {
            this.birthYear = birthYear;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withState(String state) {
            this.state = state;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withPersonalityType(String personalityType) {
            this.personalityType = personalityType;
            return this;
        }

        public Builder withAboutMe(String aboutMe) {
            this.aboutMe = aboutMe;
            return this;
        }
        public Builder withHobbies(List<String> hobbies) {
            this.hobbies = copyToList(hobbies);
            return this;
        }

        public Builder withConnexions(List<String> connexions) {

            this.connexions = copyToList(connexions);
            return this;
        }

        public UserModel build() {
            return new UserModel(id,
                                email,
                                firstName,
                                lastName,
                                gender,
                                birthMonth,
                                birthDay,
                                birthYear,
                                city,
                                state,
                                country,
                                personalityType,
                                hobbies,
                                aboutMe,
                                connexions);
        }
    }
}
