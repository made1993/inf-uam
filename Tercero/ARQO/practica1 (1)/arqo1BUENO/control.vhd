----------------------------------------------------------------------
-- Fichero: control.vhd
-- Codigo original de EC: Luis Cayola y Roberto Garcia
--	Codigo modificado de ARQO: Mario Valdemaro, Garcia Roque y Alexandru Costinel Glont
-- Asignatura: ARQO
-- Grupo de Prácticas: 1311
-- Pareja: 01
-- Práctica: 1
----------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity control is
	port (
		OPCode : in std_logic_vector(31 downto 26);
		Funct : in std_logic_vector(5 downto 0);
		MemToReg : out std_logic;
		MemWrite : out std_logic;
		Branch : out std_logic;
		ALUControlUC : out std_logic_vector(2 downto 0);
		ALUSrc : out std_logic;
		RegDest : out std_logic;
		RegWrite : out std_logic;
		Jump : out std_logic
	);
end control;

architecture UC of control is

begin


MemToReg <= '1' when OPCode="100011" else --lw
             '0'; --el resto de instrucciones
				 
				 
MemWrite <= '1' when OPCode="101011" else --sw
             '0'; --el resto de instrucciones
				 
				 
Branch <= '1' when OPCode="000101" else  
				'0';
				
				 
ALUSrc <= '0' when OPCode="000000" else --R-Type
             '1'; --el resto de instrucciones
				 
				 
RegDest <= '1' when OPCode="000000" else --R-Type
             '0'; --el resto de instrucciones
				 
				 
RegWrite <= '1' when OPCode="000000" else --R-Type
            '1' when OPCode="100011" else --lw
				'1' when OPCode="001111" else --lui
				'1' when OPCode="001010" else --slti
				'0'; --el resto de instrucciones
				
				
Jump <= '1' when OPCode="000010" else --j
             '0'; --el resto de instrucciones		 


ALUControlUC <="010" when OPCode="000000" and Funct="100010" else --R-Type (sub)
					"100" when OPCode="000000" and Funct="100100" else --R-Type (and)
					"101" when OPCode="000000" and Funct="100101" else --R-Type (or)
					"110" when OPCode="000000" and Funct="100110" else --R-Type (xor)
					"000" when OPCode="000000" and Funct="100000" else --R-Type (add)
					"111" when OPCode="001010" else --slti
					"011" when OPCode="001111" else--lui
					"000" when OPCode="100011" else
					"000" when OPCode="101011" else 
					"001"; --lui
					
end UC;