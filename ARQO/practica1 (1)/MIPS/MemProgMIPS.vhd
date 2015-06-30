----------------------------------------------------------------------
-- Fichero: MemProgMIPS.vhd
-- Descripción: Memoria de programa para el MIPS
-- Fecha última modificación: 2013-02-27

-- Autores: Alberto Sánchez (2013), Alberto Sánchez (2012), Ángel de Castro (2010) 
-- Asignatura: E.C. 1º grado
-- Grupo de Prácticas:
-- Grupo de Teoría:
-- Práctica: 5
-- Ejercicio: 3
----------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.std_LOGIC_arith.ALL;
use IEEE.std_logic_unsigned.ALL;

entity MemProgMIPS is
	port (
		MemProgAddr : in std_logic_vector(31 downto 0); -- Dirección para la memoria de programa
		MemProgData : out std_logic_vector(31 downto 0) -- Código de operación
	);
end MemProgMIPS;

architecture Simple of MemProgMIPS is

begin

	LecturaMemProg: process(MemProgAddr)
	begin
		-- La memoria devuelve un valor para cada dirección.
		-- Estos valores son los códigos de programa de cada instrucción,
		-- estando situado cada uno en su dirección.
		case MemProgAddr is
			when X"00000000" => MemProgData <= X"20010004";
			when X"00000004" => MemProgData <= X"3402000f";
			when X"00000008" => MemProgData <= X"20030cfc";
			when X"0000000C" => MemProgData <= X"306405a9";
			when X"00000010" => MemProgData <= X"28050fff";
			when X"00000014" => MemProgData <= X"2805ffff";
			when X"00000018" => MemProgData <= X"0081302a";
			when X"0000001C" => MemProgData <= X"0c000013";
			when X"00000020" => MemProgData <= X"10240001";
			when X"00000024" => MemProgData <= X"11090001";
			when X"00000028" => MemProgData <= X"00005020";
			when X"0000002C" => MemProgData <= X"214bffff";
			when X"00000030" => MemProgData <= X"00816020";
			when X"00000034" => MemProgData <= X"ac0c200c";
			when X"00000038" => MemProgData <= X"8c0d200c";
			when X"0000003C" => MemProgData <= X"00217027";
			when X"00000040" => MemProgData <= X"01c07024";
			when X"00000044" => MemProgData <= X"00247825";
			when X"00000048" => MemProgData <= X"08000012";
			when X"0000004C" => MemProgData <= X"8c072000";
			when X"00000050" => MemProgData <= X"8c282000";
			when X"00000054" => MemProgData <= X"8c092008";
			when X"00000058" => MemProgData <= X"00e85022";
			when X"0000005C" => MemProgData <= X"03e00008";		
			when others => MemProgData <= X"00000000"; -- Resto de memoria vacía
		end case;
	end process LecturaMemProg;

end Simple;

