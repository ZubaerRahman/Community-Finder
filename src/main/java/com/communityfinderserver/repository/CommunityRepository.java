package com.communityfinderserver.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.communityfinderserver.model.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Community save(Community community) {
        dynamoDBMapper.save(community);
        return community;
    }

    public Community getCommunityById(String id) {
        return dynamoDBMapper.load(Community.class, id);
    }

    public String delete(String id) {
        Community community = dynamoDBMapper.load(Community.class, id);
        dynamoDBMapper.delete(community);
        return "Community removed";
    }

    public String update(String id, Community community) {
        dynamoDBMapper.save(community,
                new DynamoDBSaveExpression().withExpectedEntry("id",
                        new ExpectedAttributeValue(
                                new AttributeValue().withS(id)
                        )));
        return id;
    }
}
