----------------------------------------------------------------------
-- Fichero: procesador.vhd
-- Codigo original de EC: Luis Cayola y Roberto Garcia
--	Codigo modificado de ARQO: Mario Valdemaro, Garcia Roque y Alexandru Costinel Glont
-- Asignatura: ARQO
-- Grupo de Pr醕ticas: 1311
-- Pareja: 01
-- Pr醕tica: 1
----------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.all;

entity procesador is
port(
	Clk         : in  std_logic;
	Reset       : in  std_logic;
	-- Instruction memory
	I_Addr      : out std_logic_vector(31 downto 0); -- MemProgAddr
	I_DataIn    : in  std_logic_vector(31 downto 0); -- MemProgData
	-- Data memory
	D_Addr      : out std_logic_vector(31 downto 0); -- Red/MemDadaAddr
	D_WrEn     	: out std_logic; -- MemDataWe
	D_DataOut   : out std_logic_vector(31 downto 0); -- MemDataDataWrite
	D_DataIn    : in  std_logic_vector(31 downto 0) -- MemDadaDataRead
);
end procesador;


architecture procesador_arq of procesador is 
signal MemDDW_EX_MEM,D_Addr_EX_MEM,ExtSing_ID_EX,Rd2_ID_EX,Rd1_ID_EX,instruccion_IF,instruccion_ID, D_DataIn_MEM_WB,  D_Addr_MEM_WB: std_logic_vector(31 downto 0);
signal auxId,auxPc,auxRd1,auxRd2,auxWd3,auxMuxAluSrc,auxMuxJump,auxRes,auxMuxPcSrc,BeqAddr1,BeqAddr2,sigExtSigno,JumpAddr,auxPC4,auxPC4_ID: std_logic_vector(31 downto 0);
signal enable_PC,enable_MEM_WB,enable_EX_MEM,auxMemWrite,MemWrite_EX_MEM,MemToReg_EX_MEM,RegWrite_EX_MEM,AluSrc_ID_EX,MemWrite_ID_EX,MemToReg_ID_EX,RegWrite_ID_EX,enable_ID_EX,enable_IF_ID,auxClk,auxEq, auxZ, auxWe3,auxMemToReg,auxBranch,auxAluSrc,auxRegDest,auxJump,auxEqInv,auxPcSrc: std_logic;
signal RegWrite_MEM_WB, MemToReg_MEM_WB, bubble :std_logic;
signal AluControl_ID_EX,auxAluControl :std_logic_vector(2 downto 0);
signal muxRegDest_EX_MEM,muxRegDest_ID_EX,auxA3, muxRegDest_MEM_WB, muxRegDest: std_logic_vector(4 downto 0);

	
	
component ALU --definimos la ALU
	port (
		Op1: in std_logic_vector(31 downto 0); -- Operando
		Op2: in std_logic_vector(31 downto 0); -- Operando
		ALUControl: in std_logic_vector(2 downto 0); -- Operacion
		Res: out std_logic_vector(31 downto 0); -- Resultado
		Z: out std_logic -- Bandera
	);
end component;




component control
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
	end component;
	



component registros
		port (
		Clk : in std_logic; -- Reloj
		Reset : in std_logic; -- Reset as铆ncrono a nivel bajo
		A1 : in std_logic_vector(4 downto 0); -- Direcci贸n para el puerto Rd1
		Rd1 : out std_logic_vector(31 downto 0); -- Dato del puerto Rd1
		A2 : in std_logic_vector(4 downto 0); -- Direcci贸n para el puerto Rd2
		Rd2 : out std_logic_vector(31 downto 0); -- Dato del puerto Rd2
		A3 : in std_logic_vector(4 downto 0); -- Direcci贸n para el puerto Wd3
		Wd3 : in std_logic_vector(31 downto 0); -- Dato de entrada Wd3
		We3 : in std_logic -- Habilitaci贸n del banco de registros
	);
	end component;


begin

-- instruccion_ID(25:21) == Rs
-- instruccion_ID(20:16) == Rt
-- instruccion_ID(20:16)/(15:11) == Rd


--process (RegWrite_ID_EX,instruccion_ID,muxRegDest_ID_EX,muxRegDest_EX_MEM,RegWrite_EX_MEM)
process(Clk, Reset)
	begin 
		if (RegWrite_ID_EX ='1' and (muxRegDest_ID_EX/=0) and (instruccion_ID(25 downto 21)=muxRegDest_ID_EX)) or
			(RegWrite_ID_EX ='1' and (muxRegDest_ID_EX/=0) and (instruccion_ID(20 downto 16)=muxRegDest_ID_EX)) or
			(RegWrite_EX_MEM ='1' and (muxRegDest_EX_MEM/=0)and (instruccion_ID(25 downto 21)=muxRegDest_EX_MEM)) or
			(RegWrite_EX_MEM ='1' and (muxRegDest_EX_MEM/=0)and (instruccion_ID(20 downto 16)=muxRegDest_EX_MEM )) then
				enable_PC<='0';
				bubble <= '1';
				enable_IF_ID<='0';
		else 
			bubble<='0';
			enable_PC <='1';
			enable_IF_ID<='1';
	end if;
end process;
--segmento IF-ID
process (Clk,Reset)
	begin
		if reset = '1' then
			auxPC4_ID <= (others =>'0'); -- PC+4
			instruccion_ID <= (others =>'0'); -- MemProgData
		elsif rising_edge(clk) and enable_IF_ID='1'then
			auxPC4_ID <= auxPC4; -- PC+4
			instruccion_ID <= I_DataIn; -- MemProgData
		
		end if;
end process;
	--instruccion_IF <= I_DataIn; -- MemProgData



-- Segmento ID-EX
 
process (Clk,Reset)
	begin
		if reset = '1' or bubble = '1' then
			-- Unidad de Control:
			RegWrite_ID_EX <= '0'; -- RegWrite
			MemToReg_ID_EX <= '0'; -- MemToReg
			MemWrite_ID_EX <= '0'; -- MemWrite
			AluControl_ID_EX <= "000"; -- ALUControl
			AluSrc_ID_EX <= '0'; -- ALUSrc
			-- Banco de registros:
			Rd1_ID_EX <= (others=>'0'); -- Rd1
			Rd2_ID_EX <= (others=>'0'); -- Rd2
			-- Otras se馻les:
			ExtSing_ID_EX <= (others=>'0'); -- Extension de Signo
			muxRegDest_ID_EX <= (others=>'0'); -- Multiplexor RegDest
	elsif rising_edge(clk) then
			-- Unidad de Control:
			RegWrite_ID_EX <= auxWe3; -- RegWrite
			MemToReg_ID_EX <= auxMemToReg; -- MemToReg
			MemWrite_ID_EX <= auxMemWrite; -- MemWrite
			AluControl_ID_EX <= auxAluControl; -- ALUControl
			AluSrc_ID_EX <= auxAluSrc; -- ALUSrc
			-- Banco de registros:
			Rd1_ID_EX <= auxRd1; -- Rd1
			Rd2_ID_EX <= auxRd2; -- Rd2
			-- Otras se馻les:
			ExtSing_ID_EX <= sigExtSigno; -- Extension de signo
			muxRegDest_ID_EX <= muxRegDest; -- Multiplexor RegDest


	end if;
end process;
	--enable_ID_EX <= '1';


-- Segmento EX-MEM


process (Clk,Reset)
	begin
		if Reset = '1'  then
			-- Unidad de Control:
			RegWrite_EX_MEM <= '0'; -- RegWrite
			MemToReg_EX_MEM <= '0'; -- MemToReg
			MemWrite_EX_MEM <= '0'; -- MemWrite
			-- Salida ALU/Entrada Memoria Datos:
			D_Addr_EX_MEM <= (others=>'0'); -- Res/MemDataAddr
			-- Entrada Memoria de Datos:
			MemDDW_EX_MEM <= (others=>'0'); -- MemDataDataWrite
			-- Otras se馻les:
			muxRegDest_EX_MEM <= (others => '0'); -- Multiplexor RegDest
		elsif rising_edge(Clk) then
			-- Unidad de Control:
			RegWrite_EX_MEM <= RegWrite_ID_EX; -- RegWrite
			MemToReg_EX_MEM <= MemToReg_ID_EX; -- MemToReg
			MemWrite_EX_MEM<= MemWrite_ID_EX; -- MemWrite
			-- Salida ALU/Entrada Memoria Datos:
			D_Addr_EX_MEM <= auxRes; -- Res/MemDataAddr
			-- Entrada Memoria de Datos:
			MemDDW_EX_MEM <= Rd2_ID_EX; -- MemDataDataWrite
			-- Otras se馻les:
			muxRegDest_EX_MEM <= muxRegDest_ID_EX; -- Multiplexor RegDest
	end if;
end process;
--enable_EX_MEM <= '1';
--segmento MEM-WB

process (Clk,Reset)
	begin
		if Reset = '1'  then
			-- Unidad de Control:
			RegWrite_MEM_WB <= '0'; -- RegWrite
			MemToReg_MEM_WB <= '0'; -- MemToReg
			-- Salida ALU:
			D_Addr_MEM_WB <= (others=>'0'); -- Res/MemDataAddr
			-- Salida Memoria Datos:
			D_DataIn_MEM_WB <= (others=>'0'); -- MemDataDataRead
			-- Otras se馻les:
			muxRegDest_MEM_WB <= (others=>'0'); -- Multiplexor de RegDest
		elsif rising_edge(Clk)  then
			-- Unidad de Control:
			RegWrite_MEM_WB <= RegWrite_EX_MEM; -- RegWrite
			MemToReg_MEM_WB <= MemToReg_EX_MEM; -- MemToReg
			-- Salida ALU:
			D_Addr_MEM_WB <= D_Addr_EX_MEM; -- Res/MemDataAddr
			-- Salida Memoria Datos:
			D_DataIn_MEM_WB <= D_DataIn; -- MemDataDataRead
			-- Otras se馻les:
			muxRegDest_MEM_WB <= muxRegDest_EX_MEM; -- Multiplexor de RegDest
	end if;
end process;

 --enable_MEM_WB <= '1';

--Registro contador de programa (PC). Se incrementa en 4 cada ciclo de reloj. El Reset as铆ncrono.
process (Clk,Reset)
	begin 
		if Reset = '1' then 
			auxPc<=(others =>'0');
		elsif rising_edge (Clk) and  enable_PC = '1' then 
			auxPc<= auxMuxJump;
		end if;
end process;
	
	I_Addr <= auxPc;
	
--Instanciaci贸n del banco de registros.
miregs: registros
port map
(		Clk =>Clk,
		Reset =>Reset,
		A1 => instruccion_ID(25 downto 21),--VOY A LEER UNA ENTRADA
		Rd1 => auxRd1, -- Dato del puerto Rd1
		A2 => instruccion_ID(20 downto 16), -- Direcci贸n para el puerto Rd2
		Rd2 => auxRd2, -- Dato del puerto Rd2
		A3 => auxA3, -- Direcci贸n para el puerto Wd3
		Wd3 =>auxWd3, -- Dato de entrada Wd3
		We3 => RegWrite_MEM_WB -- Habilitaci贸n del banco de registros
		
	);


--Instanciaci贸n de la ALU.
miALU: ALU
port map
(		Op1 => Rd1_ID_EX , -- Operando
		Op2 => auxMuxAluSrc, -- Operando 
		ALUControl => AluControl_ID_EX, -- Operacion
		Res => auxRes, -- Resultado. Ser谩 lo que le entre a D_Addr
		Z => auxZ -- Bandera
);

--Instanciaci贸n de la unidad de Control.
miUC: control
port map
(		OPCode=>instruccion_ID(31 downto 26),
		Funct =>instruccion_ID(5 downto 0),
		MemToReg => auxMemToReg,
		MemWrite => auxMemWrite, 
		Branch =>auxBranch,
		ALUControlUC =>auxAluControl,
		ALUSrc=>auxAluSrc,
		RegDest =>auxRegDest,
		RegWrite =>auxWe3,
		Jump =>auxJump
);


auxPC4 <= auxPC+4;

D_WrEn <= MemWrite_EX_MEM;

D_DataOut <= MemDDW_EX_MEM;

D_Addr<=D_Addr_EX_MEM; --Asignamos a D_Addr la auxiliar del resultado de la ALU

--Extensi贸n de Signo (Si el msb es 1, extiende a 1.Si es 0, extiende a 0)
sigExtSigno<= (x"0000" & instruccion_ID(15 downto 0)) when instruccion_ID(15)='0' else (x"FFFF" & instruccion_ID(15 downto 0));


--Direcci贸n para el salto condicional de la instrucci贸n beq
BeqAddr1<= sigExtSigno(29 downto 0) & "00";
BeqAddr2<= BeqAddr1 + auxPC4_ID;

--Direcci贸n para jump
JumpAddr<= auxPC4_ID(31 downto 28)& instruccion_ID(25 downto 0) & "00";


--Mux de ALUSrc
auxMuxAluSrc<=Rd2_ID_EX when AluSrc_ID_EX = '0' else ExtSing_ID_EX;

--Mux de MemToReg
auxWd3 <= D_Addr_MEM_WB when MemToReg_MEM_WB = '0' else D_DataIn_MEM_WB;

--Mux de RegDest
muxRegDest<= instruccion_ID(20 downto 16) when auxRegDest='0' else instruccion_ID(15 downto 11);

-- A3 del Banco de Registros:
auxA3<= muxRegDest_MEM_WB;

--Puerta AND para el PCSrc 
auxPcSrc<= auxEqInv and auxBranch;
--Inversion de auxEq
auxEqInv<= (not auxEq);--inversion

--Mux de PcSrc
auxMuxPcSrc<= auxPC4 when auxPcSrc='0' else BeqAddr2;

--Mux de Jump
auxMuxJump <=  auxMuxPcSrc when auxJump ='0' else JumpAddr;

--Se帽al AuxEq
auxEq <= '1' when auxRd1 = auxRd2 else '0';


end procesador_arq;


