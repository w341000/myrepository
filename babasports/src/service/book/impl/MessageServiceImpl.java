package service.book.impl;


import org.springframework.stereotype.Service;

import service.book.MessageService;
import bean.book.Message;
import dao.impl.BaseDaoImpl;
@Service("messageService")
public class MessageServiceImpl extends BaseDaoImpl<Message> implements MessageService {

}
