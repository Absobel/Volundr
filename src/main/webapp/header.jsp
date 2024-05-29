<style>
    /* CSS Reset */
    *,
    *::before,
    *::after {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font: inherit;
        vertical-align: baseline;
    }

    /* Custom Styles */
    @import url('https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap');

    .myheader {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 1rem;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        margin: 0;
    }

    .myimg {
        width: 4%;
        height: auto;
        display: block;
        margin: 0;
    }

    .mybutton {
        background-color: #E9E9ED;
    }

    .header-content {
        display: flex;
        gap: 1rem;
        margin: 0 0;
    }

    .myp {
        margin: 0;
    }
</style>

<header class="myheader">
    <img class="myimg" src="images/file-21N81kHTDyTlO2p7v7pJN4am-removebg-preview.png" alt="Icon"
        onclick="window.location.href='/Volundr/Serv'">
    <form action="Serv" method="post" class="header-content">
        <button class="mybutton" type="submit" name="op" value="listeUserEvent">
            Mes Evenements
        </button>
        <button class="mybutton" type="submit" name="op" value="listeUserGroupe">
            Mes Groupes
        </button>
        <button class="mybutton" type="submit" name="op" value="deconnexion">
            Deconnexion
        </button>
    </form>
</header>