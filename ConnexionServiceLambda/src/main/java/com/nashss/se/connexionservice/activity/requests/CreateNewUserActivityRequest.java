package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToList;

@JsonDeserialize(builder = CreateNewUserActivityRequest.Builder.class)
public class CreateNewUserActivityRequest {
    private final String id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final int birthMonth;
    private final int birthDay;
    private final int birthYear;
    private final String city;
    private final String state;
    private final String country;
    private final String personalityType;
    private final List<String> hobbies;
    private final String aboutMe;
    private final List<String> connexions;


    /**
     * Constructor for UpdateUserProfileActivityRequest.
     * @param id user's ID
     * @param email user's email
     * @param firstName user's first name
     * @param lastName user's last name
     * @param gender user's gender
     * @param birthMonth user's birth month
     * @param birthDay user's birth day
     * @param birthYear user's birth year
     * @param city user's city
     * @param state user's state
     * @param country user's country
     * @param personalityType user's personality type
     * @param hobbies List of user's hobbies
     * @param aboutMe user's About Me
     * @param connexions List of user's connexions
     */
    public CreateNewUserActivityRequest(String id,
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
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.birthYear = birthYear;
        this.city = city;
        this.state = state;
        this.country = country;
        this.personalityType = personalityType;
        this.hobbies = hobbies;
        this.aboutMe = aboutMe;
        this.connexions = connexions;
    }


    public String getId() {
        return id;
    }
    public String getEmail() { return email; }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() { return lastName; }
    public String getGender() { return gender; }
    public int getBirthMonth() { return birthMonth; }
    public int getBirthDay() { return birthDay; }
    public int getBirthYear() { return birthYear; }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getCountry() { return country; }
    public String getPersonalityType() {
        return personalityType;
    }
    public List<String> getHobbies() {
        return copyToList(hobbies);
    }
    public String getAboutMe() { return aboutMe; }
    public List<String> getConnexions() {
        return copyToList(connexions);
    }

    @Override
    public String toString() {
        return "CreateNewUserActivityRequest{" +
                "id='" + id + '\'' +
                "email='" + email + '\'' +
                "firstName='" + firstName + '\'' +
                "lastName='" + lastName + '\'' +
                "gender='" + gender + '\'' +
                "birthMonth='" + birthMonth + '\'' +
                "birthDay='" + birthDay + '\'' +
                "birthYear='" + birthYear + '\'' +
                "city='" + city + '\'' +
                "state='" + state + '\'' +
                "country='" + country + '\'' +
                "personalityType='" + personalityType + '\'' +
                "hobbies='" + hobbies + '\'' +
                "aboutMe='" + aboutMe + '\'' +
                "connexions='" + connexions + '\'' +
                '}';
    }


    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String email;
        private String firstName;
        private String lastName;
        private String gender;
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

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

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


        public CreateNewUserActivityRequest build() {
            return new CreateNewUserActivityRequest(id,
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
