/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rispoinditore2;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 *
 * @author davide
 */
public class Graph
{
    private LinkedHashMap<String, LinkedList<Node>> graph = new LinkedHashMap<>();
    private LinkedList<Node> status = new LinkedList<>();//list of the status of the machine

    public Graph()
    {
	for (int i = 0; i < 26; i++)
	{
	    switch (i)
	    {
		case 0:
		{
		    status.add(i, new Node("In this little adventure you have to choose between a normal family night dinner outside \n"
			    + "or an adventure but choose wisley.\n", "choice:Carry on"));
		    graph.put("Introduction", status);
		    break;
		}
		case 1:
		{
		    status.add(i, new Node("-Welcome to Freddy Fazbear's Pizzaria.\n Are you here for a birthday party?- They sure have a warm welcome \n", "choice:Yes/No"));
		    break;
		}
		case 2:
		{
		    status.add(i, new Node("Of course you're here for you're child party.\nYour family follow the waiter down the aisle "
			    + "your looking around the local is an old 60's style you choose this local for your sons birthday\n"
			    + "because he love the animatronics in here. Suddenly your child turn towards you and say <Thank you for the party>\n", "choice:No problem"));
		    break;
		}
		case 3:
		{
		    status.add(i, new Node("As you say that word you're child eyes start sparkling and you're wife seems happy,\n"
			    + "After 30 minutes of starving the waiter arrives -May I take your order?-\n", "choice:house speciality"));
		    break;
		}
		case 4:
		{
		    status.add(i, new Node("While you're waiting you're pizza you're child play with the oder invited,\n"
			    + "he also dance and sing with the animatronics. Even if you're adult you think that animatronics in particular are kinda creepy\n"
			    + "staring the for too long make your bone shake, lucky for you you're not the security guard. For destracting yourself you start a conversation with you're wife.\n"
			    + "-So he turn 10 today, damn time really go fast-you said.-Yeah it's seem like it was only yesterday that he was born.Hey do you remember that day?-she replay\n", "choice:Yes of course"));
		    break;
		}
		case 5:
		{
		    status.add(i, new Node("-Of course i remeber honey i wait outside of the you're hospital room for 4 hours to see him.-\n"
			    + "Maybe i should ask more about her family?\n", "choice:Ask"));
		    break;
		}
		case 6:
		{
		    status.add(i, new Node("You know you never talked about your father or mather, i really wonder why?-You said-\n"
			    + "I never talked about him because he was so strict, i know back in the 60's was difficult but\n"
			    + "time keep going on and so we do, you can't be firm with your mind otherwise the world leave you behind,\n "
			    + "look it's not like i hate him it's just difficult he lived the second world war he saw people that considered his family dying,\n"
			    + "and after that he went to vietnam but that time he saw poeple burning alive. Humanity can be real cruel sometimes.\n"
			    + "Anyway he was always kind with me and treat me like a princess, but when i turn 16 he became really strict woth me.\n"
			    + "My mother was an housewife, from when she was a child did house work not really small, more precisley when she turn 12;\n"
			    + "my mother met my father after the seocnd world war, they were leteral corrispences and after the war they promise to met each other.\n", "choice:Interesting"));
		    break;
		}
		case 7:
		{
		    status.add(i, new Node("-Interesting but this doesn't really explain why never talked of that.-You said.\n"
			    + "-I don't really know why I didn't told you about my parents I think it never come to my mind.\n"
			    + "It's nothing traumatic so why I never told you? I think I'll never find out.\n"
			    + "As your pizza arrive you call for your son.It past only five minutes but your son isn't coming back."
			    + "Crap what do i do?\n", "choice:Obviously search him/Keep eating and wait"));
		    break;
		}
		case 8:
		{
		    status.add(i, new Node("While your wife go and ask to the waiters if they so him you deiced to search him around.\n"
			    + "Because he love the animatronics you choose to scout the palce for the animatronics with the corner of your eyes\n"
			    + "you saw one animatronics going in a room with a child,if you thought this animatronics were creepy i really want to know what you think now.\n"
			    + "Inside the room there are parts of other animatroinics and of course you child with the animatronic\n"
			    + "there is a problem the animatronic has a knife and it seem he want to harm your child, what are you gonna do?\n", "choice:Get in his way/Look around"));
		    break;
		}
		case 9:
		{
		    status.add(i, new Node("You get in his way when he swing the knife and you're child run away, but you won't do the same,\n"
			    + "that thing hurt you really bad but at least you know your child is safe.\n", "Press enter"));
		    break;
		}
		case 10:
		{
		    status.add(i, new Node("You found a pipe you're not sure what you can do with it but it's better then nothing right?\n"
			    + "You hit him in the head and rush outside of the room, and the only thing you see is blood and dead body,\n"
			    + "it seems there is a mulfanction on the animatronics and they start killing everyone,\n"
			    + "you just tough were is Anna but there is no time you cuold die if you dont hurry out of here.\n"
			    + "you're outside now and fortunatly Anna is there but what are you gonna do now no one would beleve such a story.\n", "Press enter"));
		    break;
		}
		case 11:
		{
		    status.add(i, new Node("You deiced to keep eating instead of serching your child, what could go wrong? Maybe he want to say hi to his friend.\n"
			    + "Your wife seem to think otherwise, you try to make her keep the calm and succed in it. Now passed 15 minutes and you start to worry\n"
			    + "so you start to search your child. You found him he's playing with 3 invited thank god nothing bad happened, you call for your wife to come and see he was fine.\n "
			    + "In the end the party was a success but your child got scolded because he leave without saying anything.\n", "Press enter"));
		    break;
		}
		case 12:
		{
		    status.add(i, new Node("No i booked a table for two, hte name is Smith.You reply. It's fun the way you encounter each other:\n"
			    + "both of you were late for school, and you rushed into each other(what a clichè); and you as apoliges offered a date in here,\n"
			    + "you garantee you will pay her bill too (so chivalary is not really dead huh?).After 20 minutes she arrive, she recognize you\n"
			    + " and come towards you -Sorry i'm late-\n", "choice:No problem at all/Yes I know"));
		    break;
		}
		case 13:
		{
		    status.add(i, new Node("-Yes I know you're late and this event really annoy me-You said (wasn't that a little too mean?).\n"
			    + "-I know i'm late I already apoliges myself so can we start this date?-She try to act normally and not too irratate,\n"
			    + "unfortunatly it's write all over her face that she's dissaponted from you're behavor.-Sorry for the bad starting.\n"
			    + "Look this is my first date so I'm sorry for everything until now. Enough apoliges ehi i have an idea why you dont tell me somenthing about you? \n", "Choice:Talk about yourself"));
		break;
		}
		case 14:
		{
		    status.add(i, new Node("No problem i just arrived too-it'better to not be mean now ,\n"
			    + "I want to start this date with some apoliges for this evening, i mean we both rush into each other\n"
			    + "but i think in some way it was my fault sorry.She giggle and replay-Don't worry i think it's my fault;\n"
			    + "anyway why you don't tell somenthing about you?", "\nChoice: Talk about yourself"));
		    break;
		}
		case 15:
		{
		    status.add(i, new Node("I'm just an ordinary guy, I try my best at school but not always I succed,\n"
			    + "I don't practice any sports and I'm a solitary type; I really love photography and painting as well\n"
			    + " my favorite color is black I mean it's always okay you can dress elegant with it or just say to the people\n"
			    + "to stay away from me... The conversation goes on and after 30 minutes of chit-chat the waiter arrives.Can i take you're order? He ask.\n", "choice:house speciality"));
		    break;
		}
		case 16:
		{
		    status.add(i, new Node("After you finished ordering she says-Interesting.I want to ask you why a date as an apoliges i mean you can just give me a cofee?-\n"
			    + "-This is another side of me i tend to take things to seriously and i happen to hear you where popular at school,\n"
			    + "so what make you so popular i was wandering?- You ask.\n"
			    + "She replay.I don't really know i mean i have good grades, they say that i am always cool and good looking, but other than this i can't tell.\n"
			    + "You really are popular with guys they say you are in my same year right? You asked."
			    + "Yes i am, i only hope this all apoliges thing doesn't bother you?\n", "choice:No it doesn't/To be honest..."));
		    break;
		}
		case 17:
		{
		    status.add(i, new Node("-No it doesn't, i want to know you a little more about you.-You say.-Okay\n"
			    + "as i said before i have good grades, however i'm not that confident i really love to hear music\n"
			    + "it doesn't matter wich genere just hearing it it's okay for me, i really love drawing while i hear music\n"
			    + "i draw evrey style: graffiti,impressionist,gothic;in the end the gender doesn't really matter.\n"
			    + "my favorite color is the blue because it'really deep i mean the ocean is blue and the sky too,\n"
			    + "personally when i watch that color i feel relaxed and releaved, i love play chess it's fun to try to anticipate the other.\n"
			    + "I can play gutar and piano but i am not really interested in music.My father is a doctor and my mother an housewife,\n"
			    + "the where so happy when i told i was going out with a guy, well not really only my mother was\n"
			    + "my father start ask everything about you, after this 'date' i'm gonna say i really enjoy this time.-\n"
			    + "You try to say somenthing but the pizzas arrieves.You paid the bill as promised, what are you gonna do now?\n", "choice:Say thank you/Head back home"));
		    break;
		}
		case 18:
		{
		    status.add(i, new Node("Thank you, i really enjoied this time togher too.-You replay.-It was really fun\n"
			    + "i heard rumor you were alaways serious and dull, they were wrong.\n"
			    + "I heard there is an exhibition of Claude Monet and other impressionist wuold you like to accompany me?-\n", "choice:Yes I'd love to/No I'm sorry"));
		    break;
		}
		case 19:
		{
		    status.add(i, new Node("-Yes I heard there is a beautiful painting, it's call blank dream.-You replay-I never heard that,\n"
			    + "well the is settle see you next time.\n", "Press enter"));
		    break;
		}
		case 20:
		{
		    status.add(i, new Node("To be honest it's quiet annoying, I mean it's okay to say sorry and apoliges but keep saying that\n "
			    + "can annoy someone,don't get the wrong idea.-You said.-Don't worry it'fine people always say i'm annoying and boring\n"
			    + "so i want to say sorry.-\n", "choice:you're fine/You're not boring"));
		    break;
		}
		case 21:
		{
		    status.add(i, new Node("-No i'm sorry but i'm to busy, but we can do the way back home from school togheter if you like.--Yes I like the idea"
			    + " She replay\n", "Press enter"));
		    break;
		}
		case 22:
		{
		    status.add(i, new Node("-I like workday people I think they keep the world spinning.-As you finish the sentece she blush,\n"
			    + "You're order arrive and both of you start eating.You paid the bill as promised, what are you gonna do now?\n", "choice:Say thank you/Head back home"));
		    break;
		}
		case 23:
		{
		    status.add(i, new Node("You decide to head back instead of ask her for another date. In the end the ocean is filled with fish right?\n", "Press enter"));
		    break;
		}
		case 24:
		{
		    status.add(i, new Node("-You're not boring at all, and if someone say somenthing like this just screw about his opinion-\n"
			    + "She blush again-Thank you really know how to cheer up somebody-\n"
			    + "You try to say somenthing but the pizzas arrieves.You paid the bill as promised, what are you gonna do now?\n", "choice:Say thank you/Head back home"));
		    break;
		}
		case 25:
		{
		    status.add(i, new Node("Fin", ""));
		    break;
		}

	    }

	}//added the list to introduction

    }

    public LinkedList getNode()
    {
	return status;
    }

    public Node getNode(int index)
    {
	return status.get(index);
    }
}
