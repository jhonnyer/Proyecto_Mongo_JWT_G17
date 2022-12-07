package com.mongo.app.util;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongo.app.models.DatabaseSequenceUser;

@Component
public class SequenceUsuario {
	@Autowired
	private MongoOperations mongo;
	
	public long generatedSequence(String seqName) {
		DatabaseSequenceUser counter=mongo.findAndModify(query(where("_id").is(seqName)),
				new Update().inc("seq", 1), options().returnNew(true).upsert(true),
				DatabaseSequenceUser.class);
		return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}
}
