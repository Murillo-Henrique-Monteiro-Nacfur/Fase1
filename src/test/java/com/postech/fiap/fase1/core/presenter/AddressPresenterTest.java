package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.dto.address.AddressDTO;
import com.postech.fiap.fase1.core.dto.address.AddressRequestDTO;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.core.dto.address.AddressableDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantDTO;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddressPresenterTest {
    private final AddressPresenter presenter = new AddressPresenter();

    @Test
    void requestToAddressUserDomain_shouldMapFields() {
        AddressRequestDTO dto = AddressRequestDTO.builder()
                .id(1L).street("Rua A").number("123").neighborhood("Centro")
                .city("Cidade").state("SP").country("Brasil").zipCode("00000-000").build();
        AddressDomain domain = presenter.requestToAddressUserDomain(dto, 10L);
        assertEquals(dto.getId(), domain.getId());
        assertEquals(dto.getStreet(), domain.getStreet());
        assertEquals(dto.getNumber(), domain.getNumber());
        assertEquals(dto.getNeighborhood(), domain.getNeighborhood());
        assertEquals(dto.getCity(), domain.getCity());
        assertEquals(dto.getState(), domain.getState());
        assertEquals(dto.getCountry(), domain.getCountry());
        assertEquals(dto.getZipCode(), domain.getZipCode());
        assertInstanceOf(UserDomain.class, domain.getAddressable());
        assertEquals(10L, domain.getAddressable().getId());
    }

    @Test
    void requestToAddressRestaurantDomain_shouldMapFields() {
        AddressRequestDTO dto = AddressRequestDTO.builder()
                .id(2L).street("Rua B").number("456").neighborhood("Bairro")
                .city("Cidade2").state("RJ").country("Brasil").zipCode("11111-111").build();
        AddressDomain domain = presenter.requestToAddressRestaurantDomain(dto, 20L);
        assertEquals(dto.getId(), domain.getId());
        assertEquals(dto.getStreet(), domain.getStreet());
        assertEquals(dto.getNumber(), domain.getNumber());
        assertEquals(dto.getNeighborhood(), domain.getNeighborhood());
        assertEquals(dto.getCity(), domain.getCity());
        assertEquals(dto.getState(), domain.getState());
        assertEquals(dto.getCountry(), domain.getCountry());
        assertEquals(dto.getZipCode(), domain.getZipCode());
        assertInstanceOf(RestaurantDomain.class, domain.getAddressable());
        assertEquals(20L, domain.getAddressable().getId());
    }

    @Test
    void requestUpdateToInput_shouldMapFields() {
        AddressRequestDTO dto = AddressRequestDTO.builder()
                .id(3L).street("Rua C").number("789").neighborhood("Zona Sul")
                .city("Cidade3").state("MG").country("Brasil").zipCode("22222-222").build();
        AddressDomain domain = presenter.requestUpdateToInput(dto);
        assertEquals(dto.getId(), domain.getId());
        assertEquals(dto.getStreet(), domain.getStreet());
        assertEquals(dto.getNumber(), domain.getNumber());
        assertEquals(dto.getNeighborhood(), domain.getNeighborhood());
        assertEquals(dto.getCity(), domain.getCity());
        assertEquals(dto.getState(), domain.getState());
        assertEquals(dto.getCountry(), domain.getCountry());
        assertEquals(dto.getZipCode(), domain.getZipCode());
    }

    @Test
    void toAddressDTO_shouldMapFieldsWithAddressable() {
        AddressDomain domain = AddressDomain.builder()
                .id(4L).street("Rua D").number("101").neighborhood("Norte")
                .city("Cidade4").state("RS").country("Brasil").zipCode("33333-333")
                .build();
        UserDTO userDTO = UserDTO.builder().id(100L).build();
        AddressDTO dto = presenter.toAddressDTO(domain, userDTO);
        assertEquals(domain.getId(), dto.getId());
        assertEquals(domain.getStreet(), dto.getStreet());
        assertEquals(domain.getNumber(), dto.getNumber());
        assertEquals(domain.getNeighborhood(), dto.getNeighborhood());
        assertEquals(domain.getCity(), dto.getCity());
        assertEquals(domain.getState(), dto.getState());
        assertEquals(domain.getCountry(), dto.getCountry());
        assertEquals(domain.getZipCode(), dto.getZipCode());
        assertEquals(userDTO, dto.getAddressable());
    }

    @Test
    void toDTO_shouldMapFields() {
        AddressDomain domain = AddressDomain.builder()
                .id(5L).street("Rua E").number("202").neighborhood("Leste")
                .city("Cidade5").state("SC").country("Brasil").zipCode("44444-444")
                .build();
        AddressDTO dto = presenter.toDTO(domain);
        assertEquals(domain.getId(), dto.getId());
        assertEquals(domain.getStreet(), dto.getStreet());
        assertEquals(domain.getNumber(), dto.getNumber());
        assertEquals(domain.getNeighborhood(), dto.getNeighborhood());
        assertEquals(domain.getCity(), dto.getCity());
        assertEquals(domain.getState(), dto.getState());
        assertEquals(domain.getCountry(), dto.getCountry());
        assertEquals(domain.getZipCode(), dto.getZipCode());
    }

    @Test
    void toDomain_shouldMapFieldsWithUserAddressable() {
        UserDTO userDTO = UserDTO.builder().id(200L).build();
        AddressDTO dto = AddressDTO.builder()
                .id(6L).street("Rua F").number("303").neighborhood("Oeste")
                .city("Cidade6").state("BA").country("Brasil").zipCode("55555-555")
                .addressable(userDTO)
                .build();
        AddressDomain domain = presenter.toDomain(dto);
        assertEquals(dto.getId(), domain.getId());
        assertEquals(dto.getStreet(), domain.getStreet());
        assertEquals(dto.getNumber(), domain.getNumber());
        assertEquals(dto.getNeighborhood(), domain.getNeighborhood());
        assertEquals(dto.getCity(), domain.getCity());
        assertEquals(dto.getState(), domain.getState());
        assertEquals(dto.getCountry(), domain.getCountry());
        assertEquals(dto.getZipCode(), domain.getZipCode());
        assertInstanceOf(UserDomain.class, domain.getAddressable());
        assertEquals(200L, domain.getAddressable().getId());
    }

    @Test
    void toDomain_shouldMapFieldsWithRestaurantAddressable() {
        RestaurantDTO restaurantDTO = RestaurantDTO.builder().id(300L).build();
        AddressDTO dto = AddressDTO.builder()
                .id(7L).street("Rua G").number("404").neighborhood("Centro")
                .city("Cidade7").state("PR").country("Brasil").zipCode("66666-666")
                .addressable(restaurantDTO)
                .build();
        AddressDomain domain = presenter.toDomain(dto);
        assertEquals(dto.getId(), domain.getId());
        assertEquals(dto.getStreet(), domain.getStreet());
        assertEquals(dto.getNumber(), domain.getNumber());
        assertEquals(dto.getNeighborhood(), domain.getNeighborhood());
        assertEquals(dto.getCity(), domain.getCity());
        assertEquals(dto.getState(), domain.getState());
        assertEquals(dto.getCountry(), domain.getCountry());
        assertEquals(dto.getZipCode(), domain.getZipCode());
        assertInstanceOf(RestaurantDomain.class, domain.getAddressable());
        assertEquals(300L, domain.getAddressable().getId());
    }

    @Test
    void toDomain_shouldReturnNullAddressableForUnknownType() {
        AddressableDTO unknown = () -> null;
        AddressDTO dto = AddressDTO.builder()
                .id(8L).street("Rua H").number("505").neighborhood("Desconhecido")
                .city("Cidade8").state("GO").country("Brasil").zipCode("77777-777")
                .addressable(unknown)
                .build();
        AddressDomain domain = presenter.toDomain(dto);
        assertNull(domain.getAddressable());
    }

    @Test
    void toDomainList_shouldMapList() {
        UserDTO userDTO = UserDTO.builder().id(400L).build();
        AddressDTO dto1 = AddressDTO.builder().id(9L).addressable(userDTO).build();
        AddressDTO dto2 = AddressDTO.builder().id(10L).addressable(userDTO).build();
        List<AddressDTO> dtos = List.of(dto1, dto2);
        List<AddressDomain> domains = presenter.toDomain(dtos);
        assertEquals(2, domains.size());
        assertEquals(dto1.getId(), domains.get(0).getId());
        assertEquals(dto2.getId(), domains.get(1).getId());
    }

    @Test
    void toResponseDTO_shouldMapFields() {
        AddressDomain domain = AddressDomain.builder()
                .id(11L).street("Rua I").number("606").neighborhood("Sul")
                .city("Cidade9").state("PE").country("Brasil").zipCode("88888-888")
                .build();
        AddressResponseDTO response = presenter.toResponseDTO(domain);
        assertEquals(domain.getId(), response.getId());
        assertEquals(domain.getStreet(), response.getStreet());
        assertEquals(domain.getNumber(), response.getNumber());
        assertEquals(domain.getNeighborhood(), response.getNeighborhood());
        assertEquals(domain.getCity(), response.getCity());
        assertEquals(domain.getState(), response.getState());
        assertEquals(domain.getCountry(), response.getCountry());
        assertEquals(domain.getZipCode(), response.getZipCode());
    }

    @Test
    void toResponseDTOPage_shouldMapPage() {
        AddressDomain domain1 = AddressDomain.builder().id(12L).build();
        AddressDomain domain2 = AddressDomain.builder().id(13L).build();
        Page<AddressDomain> page = new PageImpl<>(List.of(domain1, domain2));
        Page<AddressResponseDTO> responsePage = presenter.toResponseDTO(page);
        assertEquals(2, responsePage.getContent().size());
        assertEquals(domain1.getId(), responsePage.getContent().get(0).getId());
        assertEquals(domain2.getId(), responsePage.getContent().get(1).getId());
    }

    @Test
    void toResponseDTOList_shouldMapList() {
        AddressDomain domain1 = AddressDomain.builder().id(14L).build();
        AddressDomain domain2 = AddressDomain.builder().id(15L).build();
        List<AddressDomain> domains = List.of(domain1, domain2);
        List<AddressResponseDTO> responses = presenter.toResponseDTO(domains);
        assertEquals(2, responses.size());
        assertEquals(domain1.getId(), responses.get(0).getId());
        assertEquals(domain2.getId(), responses.get(1).getId());
    }
}