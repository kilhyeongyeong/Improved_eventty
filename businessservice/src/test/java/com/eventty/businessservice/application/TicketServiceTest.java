package com.eventty.businessservice.application;


import com.eventty.businessservice.event.application.dto.response.TicketResponseDTO;
import com.eventty.businessservice.event.application.service.subservices.TicketService;
import com.eventty.businessservice.event.domain.entity.TicketEntity;
import com.eventty.businessservice.event.domain.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eventty.businessservice.utils.TestUtil.createFakeTicketList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

}

