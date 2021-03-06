package org.nhnnext.guinness.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nhnnext.guinness.exception.MakingObjectListFromJdbcException;
import org.nhnnext.guinness.model.Comment;
import org.nhnnext.guinness.model.Group;
import org.nhnnext.guinness.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class CommentDaoTest {
	private static final Logger logger = LoggerFactory.getLogger(CommentDaoTest.class);
	private static final String NOTEID = "7";

	@Autowired
	private CommentDao commentDao;

	Group group = null;
	Note note = null;
	Comment comment = null;
	List<Comment> comments = null;

	@Before
	public void before() throws MakingObjectListFromJdbcException, ClassNotFoundException {
		commentDao.deleteAllCommentsByNoteId("9");
		comment = new Comment("unit test", "A", "admin@guinness.com", "9");
		commentDao.createComment(comment);
		comments = commentDao.readCommentListByNoteId(comment.getNoteId());
	}

	@Test
	public void readCommentListByNoteId() throws ClassNotFoundException {
		List<Comment> comments = commentDao.readCommentListByNoteId(NOTEID);
		for (Iterator<Comment> i = comments.iterator(); i.hasNext();) {
			Comment comment = i.next();
			logger.debug(comment.toString());
		}
		assertNotNull(comments);
	}

	@Test
	public void readCommentByCommentId() throws Exception {
		Comment dbComment = commentDao.readCommentByCommentId(comments.get(0).getCommentId());
		assertEquals(dbComment, comment);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void deleteComment() throws Exception {
		commentDao.deleteComment(comments.get(0).getCommentId());
		commentDao.readCommentByCommentId(comments.get(0).getCommentId());
	}
	
	@Test
	public void updateComment_내용만수정() throws Exception {
		commentDao.updateComment(comments.get(0).getCommentId(), "수정된 내용");
		Comment dbComment = commentDao.readCommentByCommentId(comments.get(0).getCommentId());
		assertEquals("수정된 내용", dbComment.getCommentText());
		
	}

}
