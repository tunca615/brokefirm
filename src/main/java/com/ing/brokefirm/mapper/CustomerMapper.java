package com.ing.brokefirm.mapper;

import com.ing.brokefirm.controller.resource.CustomerResource;
import com.ing.brokefirm.controller.command.CustomerCommand;
import com.ing.brokefirm.model.Customer;
import com.ing.brokefirm.repository.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );

    CustomerEntity customerToCustomerEntity(Customer customer);
    Customer customerEntityToCustomer(CustomerEntity customerEntity);
    Customer customerCommandToCustomer(CustomerCommand customerCommand);
    CustomerResource customerToCustomerResource(Customer customer);
}
