package com.fritzoli.workouttracker.dto.request.workout;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fritzoli.workouttracker.utility.PatchField;

import lombok.Getter;

@Getter
public class UpdateWorkoutRequest {
    //Todo: Custom field Validation
    private PatchField<String> name = PatchField.notProvided();
    private PatchField<String> comment = PatchField.notProvided();

    @JsonSetter(nulls = Nulls.SET)
    public void setName(String name) {
        this.name = PatchField.of(name);
    }

    @JsonSetter(nulls = Nulls.SET)
    public void setComment(String comment) {
        this.comment = PatchField.of(comment);
    }

}
