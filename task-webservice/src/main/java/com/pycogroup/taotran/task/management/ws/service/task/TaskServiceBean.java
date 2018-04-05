package com.pycogroup.taotran.task.management.ws.service.task;

import com.pycogroup.taotran.task.management.ws.entity.TaskDTO;
import com.pycogroup.taotran.task.management.ws.service.EntityServiceBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskServiceBean extends EntityServiceBean<TaskDTO> implements TaskService {

}
